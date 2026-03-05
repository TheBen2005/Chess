package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;

public class ClearService {
    private DataAccess dataAccess;

    public ClearService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clearUsers();
        dataAccess.clearAuth();
        dataAccess.clearGames();
    }
}
