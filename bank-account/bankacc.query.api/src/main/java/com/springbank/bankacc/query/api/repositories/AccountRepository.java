package com.springbank.bankacc.query.api.repositories;

import com.springbank.bankacc.core.models.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {
}
