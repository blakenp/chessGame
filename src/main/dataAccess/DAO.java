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
     * An abstract getAll method that can be used to get a list of all objects that
     * meet a query's expectations
     * @return A list of object returned from a query on the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    List<T> getAll() throws DataAccessException;

    /**
     * An abstract post method that can be used to create new objects and insert them in the database
     * @param object The object that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    void post(T object) throws DataAccessException;

    /**
     * An abstract put method that can be used to update an existing object in the database
     * @param object The object in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    void put(T object) throws DataAccessException;

    /**
     * An abstract delete method that can be used to delete an existing object in the database
     * @param object The object in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    void delete(T object) throws DataAccessException;
}
