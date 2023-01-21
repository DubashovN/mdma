package ru.filit.mdma.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.model.entity.Operation;
import ru.filit.mdma.repository.OperationRepository;
import ru.filit.mdma.service.EntityRepo;


@Repository
@Slf4j
public class OperationRepositoryImpl extends AbstractYmlRepository implements OperationRepository {

  @Value(value = "operations.yml")
  private String fileName;

  @Value(value = "${dm.repo.location}")
  private String filePath;

  private final EntityRepo entityRepo;

  private final List<Operation> operations = new CopyOnWriteArrayList<>();

  public OperationRepositoryImpl(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @PostConstruct
  public void init() {
    final List<Operation> operationList =
        entityRepo.readList(getFile(filePath, fileName), new TypeReference<>() {
        });
    operations.addAll(operationList);
  }

  @Override
  public List<Operation> getOperations(String accountNumber, int quantity) {
    Stream<Operation> sortedStream = operations.stream()
        .filter(operation -> operation.getAccountNumber().equals(accountNumber))
        .sorted(Comparator.comparing(Operation::getOperDate).reversed());
    if (quantity > 0) {
      sortedStream = sortedStream.limit(quantity);
    }
    return sortedStream.collect(Collectors.toList());
  }

  @Override
  public List<Operation> getOperationsByPeriod(String accountNumber, Long fromDate, Long toDate) {
    return operations.stream()
        .filter(operation -> {
          final Long operDate = operation.getOperDate();
          return operation.getAccountNumber().equals(accountNumber)
              && operDate.compareTo(fromDate) > 0 && operDate.compareTo(toDate) < 0;
        })
        .sorted(Comparator.comparing(Operation::getOperDate).reversed())
        .collect(Collectors.toList());
  }
}
