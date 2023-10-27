package dataAccess;

import java.util.List;

/**
 * This is an abstract interface that lays the foundations for the DAOs I have to implement by defining
 * the CRUD methods that DAOs can perform on a database. T represents any type of object, so it can work
 * for making changes to the database for every type of DAO that will be implemented.
 *
 * @param <T> An abstract Type that represent any type
 */
public interface DAO<T> {

    /**
     * An abstract method that queries for a specific object of type T and returns it if it exists in the database
     * @param object The object that will be queried for in the database
     * @return The object that was passed in if it exists in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    T get(T object) throws DataAccessException;

    /**
     * An abstract post method that can be used to create new objects and insert them in the database
     * @param object The object that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    void post(T object) throws DataAccessException;

    /**
     * An abstract delete all method that deletes all of a specific type of object in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    void deleteAll() throws DataAccessException;
}
