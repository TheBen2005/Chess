package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearServiceTests {
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        clearService = new ClearService(dataAccess);
        clearService.clear();
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        clearService.clear();
    }

}
