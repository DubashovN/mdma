package ru.filit.mdma.model.cache;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.web.TokenUtil;
import ru.filit.mdma.web.dto.AccessDto;


@Component
public class TokenService {

  private final SensitiveDataCache dataCache;

  public TokenService(SensitiveDataCache dataCache) {
    this.dataCache = dataCache;
  }

  public <T extends Serializable> void detokenizeObject(T target) {
    Class<? extends Serializable> targetClass = target.getClass();
    PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(target);
    for (Field field : getProperties(targetClass)) {
      Object value = myAccessor.getPropertyValue(field.getName());
      if (value instanceof String) {
        if (TokenUtil.isTokenized((String) value)) {
          final String cachedData = dataCache.read((String) value);
          myAccessor.setPropertyValue(field.getName(), cachedData);
        }
      }
    }
  }

  public <T extends Serializable> void tokenizeObject(@NotNull T target,
      List<AccessDto> access, String entity) {
    Class<? extends Serializable> targetClass = target.getClass();
    final Set<String> fieldsToTokenize = getFieldsToMask(access, entity, targetClass);
    tokenizeFields(getProperties(targetClass), fieldsToTokenize, target);
  }

  public <T extends Serializable> void tokenizeObject(@NotNull List<T> target,
      List<AccessDto> access, String entity) {
    if (target.isEmpty()) {
      return;
    }
    Class<? extends Serializable> targetClass = target.get(0).getClass();
    final Set<String> fieldsToTokenize = getFieldsToMask(access, entity, targetClass);
    target.forEach(t ->
        tokenizeFields(getProperties(targetClass), fieldsToTokenize, t));
  }

  private <T extends Serializable> void tokenizeFields(Field[] allFields,
      Set<String> fieldsToMask, T t) {
    PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(t);

    for (Field property : allFields) {
      String field = property.getName();
      final Object propertyValue = myAccessor.getPropertyValue(field);
      if (propertyValue instanceof String) {
        String fieldValue;
        if (TokenUtil.isTokenized((String) propertyValue)) {
          fieldValue = dataCache.read((String) propertyValue);
        } else {
          fieldValue = (String) propertyValue;
        }
        if (fieldsToMask.contains(field)) {
          final String token = TokenUtil.buildToken();
          if (dataCache.put(token, fieldValue)) {
            myAccessor.setPropertyValue(field, token);
          } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to put tokenized data in cache");
          }
        } else {
          myAccessor.setPropertyValue(field, fieldValue);
        }
      }
    }
  }

  private Set<String> getFieldsToMask(List<AccessDto> access, @NotNull String entity,
      Class<? extends Serializable> targetClass) {
    Set<String> visibleFields = access.stream()
        .filter(accessDto -> entity.equals(accessDto.getEntity()))
        .map(AccessDto::getProperty)
        .collect(Collectors.toSet());

    Set<String> fieldsToHide = Arrays.stream(getProperties(targetClass))
        .map(Field::getName).collect(Collectors.toSet());
    fieldsToHide.removeAll(visibleFields);
    return fieldsToHide;
  }

  private Field[] getProperties(Class<? extends Serializable> targetClass) {
    return Arrays.stream(targetClass.getDeclaredFields())
        .filter(field -> {
          int mod = field.getModifiers();
          return !Modifier.isFinal(mod) && !Modifier.isStatic(mod);
        })
        .toArray(Field[]::new);
  }
}
