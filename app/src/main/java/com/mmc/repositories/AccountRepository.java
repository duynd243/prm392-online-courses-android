package com.mmc.repositories;

import com.mmc.api.APIClient;
import com.mmc.services.AccountService;
import com.mmc.services.CourseService;

public class AccountRepository {
    public static AccountService getAccountService() {
        return APIClient.getAPIClient().create(AccountService.class);
    }
}
