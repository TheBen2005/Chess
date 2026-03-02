package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;

public class ClearService {
    DataAccess dataAccess = new MemoryDataAccess();
    public void clear() throws DataAccessException {
        dataAccess.clearUsers();
        dataAccess.clearAuth();
        dataAccess.clearGames();

    }
}
