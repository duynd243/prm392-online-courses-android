package com.mmc.repositories;

import com.mmc.api.APIClient;
import com.mmc.services.AccountService;

public class AccountRepository {
    public static AccountService getAccountService() {
        return APIClient.getAPIClient().create(AccountService.class);
    }
}
