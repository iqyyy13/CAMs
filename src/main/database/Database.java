package main.database;

import main.model.Model;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.Preservable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Database<ModelObject extends Model> extends Preservable<ModelObject> implements Iterable<ModelObject> 
{


    List<ModelObject> listOfModelObjects;

    /**
     * Creates a new instance of the Repository class.
     */
    public Database() 
    {
        super();
        listOfModelObjects = new ArrayList<>();
    }

  
    /**
     * Gets the path of the repository file.
     *
     * @return the path of the repository file
     */
    public abstract String getFilePath();

    /**
     * Gets the list of mappable objects.
     *
     * @return the list of mappable objects
     */
    @Override
    protected List<ModelObject> getAll() 
    {
        return listOfModelObjects;
    }

    /**
     * Gets a model object by ID
     *
     * @param modelObjectID the ID of the model object to get
     * @return the model object with the given ID
     * @throws UserErrorException if the model object with the given ID does not exist
     */
    public ModelObject getByID(String modelObjectID) throws UserErrorException 
    {
        for (ModelObject modelObject : listOfModelObjects) 
        {
            if (modelObject.getID().equalsIgnoreCase(modelObjectID)) 
            {
                return modelObject;
            }
        }
        throw new UserErrorException("No model object with ID " + modelObjectID + " exists.");
    }

    /**
     * Checks whether the repository contains a model object with the given ID.
     *
     * @param modelObjectID the ID of the model object to check
     * @return true if the repository contains a model object with the given ID, false otherwise
     */
    public boolean contains(String modelObjectID) 
    {
        try 
        {
            getByID(modelObjectID);
            return true;
        } catch (UserErrorException e) {
            return false;
        }
    }

    /**
     * Adds a model object to the repository.
     *
     * @param modelObject the model object to add
     * @throws ModelAlreadyExistsException if a model object with the same ID already exists in the repository
     */
    public void add(ModelObject modelObject) throws UserAlreadyExistsException 
    {
        if (contains(modelObject.getID())) 
        {
            throw new UserAlreadyExistsException("A model object with ID " + modelObject.getID() + " already exists.");
        } 
        else 
        {
            listOfModelObjects.add(modelObject);
            save(getFilePath());
        }
    }

    /**
     * Removes a model object from the repository by ID.
     *
     * @param modelObjectID the ID of the model object to remove
     * @throws UserErrorException if the model object with the given ID does not exist
     */
    public void remove(String modelObjectID) throws UserErrorException 
    {
        listOfModelObjects.remove(getByID(modelObjectID));
        save(getFilePath());
    }

    /**
     * Checks whether the repository is empty.
     *
     * @return true if the repository is empty, false otherwise
     */
    public boolean isEmpty() 
    {
        return listOfModelObjects.isEmpty();
    }

    /**
     * Gets the size of the repository.
     *
     * @return the size of the repository
     */
    public int size() 
    {
        return listOfModelObjects.size();
    }

    /**
     * Removes all model objects from this repository.
     */
    public void clear() 
    {
        listOfModelObjects.clear();
        save(getFilePath());
    }

    /**
     * Updates the specified model object in the repository.
     *
     * @param modelObject the model object to update
     * @throws UserErrorException if the specified model object is not found in the repository
     */
    public void update(ModelObject modelObject) throws UserErrorException 
    {
        ModelObject oldModelObject = getByID(modelObject.getID());
        listOfModelObjects.set(listOfModelObjects.indexOf(oldModelObject), modelObject);
        save(getFilePath());
    }

    /**
     * Updates all model objects in the repository with the specified list of model objects.
     *
     * @param modelObjects the list of model objects to update
     */
    public void updateAll(List<ModelObject> modelObjects) 
    {
        listOfModelObjects = modelObjects;
        save(getFilePath());
    }

    /**
     * Loads the list of model objects from the repository file.
     */
    public void load() 
    {
        this.listOfModelObjects = new ArrayList<>();
        load(getFilePath());
    }

    /**
     * Saves the list of model objects to the repository file.
     */
    public void save() 
    {
        save(getFilePath());
    }

    /**
     * Returns an iterator over the list of model objects of type {@code T}.
     *
     * @return an iterator over the list of model objects
     */
    @Override
    public Iterator<ModelObject> iterator() 
    {
        return listOfModelObjects.iterator();
    }

    /**
     * Finds all model objects in the repository that match the specified rules.
     * 
     * Multiple rules can be specified, and all rules must be satisfied for a model object to be considered a match.
     * 
     * The rules are specified as lambda expressions that take a model object as a parameter and return a boolean.
     *
     * @param rules the rules to match
     * @return a list of all model objects in the repository that match the specified rules
     */
    @SafeVarargs
    public final List<ModelObject> findByRules(RepositoryRule<ModelObject>... rules) {
        List<ModelObject> modelObjects = new ArrayList<>();
        for (ModelObject modelObject : listOfModelObjects) {
            boolean isMatch = true;
            for (RepositoryRule<ModelObject> rule : rules) {
                if (!rule.isMatch(modelObject)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                modelObjects.add(modelObject);
            }
        }
        return modelObjects;
    }

    /**
     * Gets a list of all model objects in the repository.
     *
     * @return a list of all model objects in the repository
     */
    public List<ModelObject> getList()
    {
        return findByRules();
    }

    /**
     * Provides a rule for filtering model objects in the repository.
     *
     * @param <ModelObject> the type of model object stored in the repository
     */
    public interface RepositoryRule<ModelObject>
    {
        /**
         * Checks whether the specified model object matches the rule.
         *
         * @param modelObject the model object to check
         * @return true if the model object matches the rule, false otherwise
         */
        boolean isMatch(ModelObject modelObject);
    }
}
