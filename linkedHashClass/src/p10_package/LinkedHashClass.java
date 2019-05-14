
package p10_package;
/**
 *
 * @author ggear
 */
public class LinkedHashClass extends java.lang.Object
{    
    private class LinkedNodeClass 
            extends java.lang.Object
    {
        SimpleStudentClass data;
        LinkedHashClass.LinkedNodeClass nextRef;
        private LinkedNodeClass(SimpleStudentClass inData)
        {
            data = inData;
            nextRef = null;
        }
    }
    /**
     * Table size default
     * <p>
     * Table size (capacity) is recommended to be a prime number
     */
    private final int DEFAULT_TABLE_SIZE = 12;
    /**
     * Size of the base table
     */
    private int tableSize;
    /**
     * Constant used to control access operation
     */
    private boolean REMOVE = true;
    /**
     * Constant used to control access operation
     */
    private boolean SEARCH = false;
    /**
     * Array for hash table
     */
    private LinkedHashClass.LinkedNodeClass[] tableArray;
    /**
     * Default constructor
     * <p>
     * Initializes array to default table size
     */
    public LinkedHashClass()
    {
        tableSize = DEFAULT_TABLE_SIZE;
        tableArray = new LinkedHashClass.LinkedNodeClass[tableSize];
        for(int index = 0; index < tableSize; index++ ) 
        {
            tableArray[index] = null;
        }
    }
    public LinkedHashClass(int inTableSize)
    {
        tableSize = inTableSize;
        tableArray = new LinkedHashClass.LinkedNodeClass[tableSize];
        for(int index = 0; index < tableSize; index++ ) 
        {
            tableArray[index] = null;
        }
    }
    /**
     * copy constructor
     * <p>
     * @param copied - LinkedHashClass object to be copied 
     */
    public LinkedHashClass(LinkedHashClass copied)
    {
        tableSize = copied.tableSize;
        tableArray = new LinkedNodeClass[tableSize];
        int index = 0;
        LinkedNodeClass thisNode, copyNode;
        while( index < copied.tableSize )
        {
            // save current and create new starting nodes            
            copyNode = copied.tableArray[index];
            if( copyNode != null )
            {
                tableArray[index] = new LinkedNodeClass(copyNode.data); 
                thisNode = tableArray[index];
                copyNode = copyNode.nextRef;
                // copy the rest of the linked list
                while( copyNode.nextRef != null )
                {
                    // save data to next node
                    thisNode.nextRef = new LinkedNodeClass(copyNode.data);
                    copyNode = copyNode.nextRef;
                    thisNode = thisNode.nextRef;
                }
            }
            else
            {
                tableArray[index] = null;
            }
            index++;            
        }
    }
    /**
     * Helper method that handles both searching and removing items in linked 
     * list at specified index
     * <p>
     * @param linkIndex - integer index specifying location in array
     * <p>
     * @param studentID - integer key for searching and/or removing node
     * <p>
     * @param removeFlag - boolean flag that indicates whether to search or
     * remove (use SEARCH, REMOVE constants to call this method)
     * <p>
     * @return SimpleStudentClass data found and/or removed
     */
    private SimpleStudentClass accessLinkedData(int linkIndex,
                                            int studentID,
                                            boolean removeFlag)
    {
    SimpleStudentClass tempVal = null;
    LinkedNodeClass wkgRef = tableArray[ linkIndex ];

    if( wkgRef != null )
       {
        if( wkgRef.data.studentID == studentID )
           {
            tempVal = wkgRef.data;

            if( removeFlag )
               {
                tableArray[ linkIndex ] = wkgRef.nextRef;
               }
           }
        else
           {
            while( wkgRef.nextRef != null 
                    && wkgRef.nextRef.data.studentID != studentID )
               {
                wkgRef = wkgRef.nextRef;
               }

            if( wkgRef.nextRef != null )
               {
                tempVal = wkgRef.nextRef.data;

                if( removeFlag )
                   {
                    wkgRef.nextRef = wkgRef.nextRef.nextRef;
                   }
               }
           }      
       }
    return tempVal;
   }


            /*
    {
        // sets first node
        LinkedNodeClass currentNode = tableArray[linkIndex];
        // check if searching for first node 
        if( currentNode.data.studentID == studentID )
        {
            // remove node if flag is set
            if( removeFlag == REMOVE )
            {
                tableArray[linkIndex] = currentNode.nextRef;
            }
            return currentNode.data;
        }
        // Finds Node before searched/removedNode
        while( currentNode.nextRef != null )
        {
            // check if arrived at searched node
            if( currentNode.nextRef.data.studentID == studentID )
            {
                // save to return
                SimpleStudentClass saveNode = currentNode.nextRef.data;
                // remove node if flag is set
                if( removeFlag == REMOVE )
                {
                    currentNode.nextRef = currentNode.nextRef.nextRef;   
                }
               return saveNode;
            }
            else
            {
                currentNode = currentNode.nextRef;
            }
        }
        // return null if never found
        return null;
    }
            */
    /**
     * Adds item to hash table
     * <p>
     * Uses overloaded addItem with object
     * <p>
     * @param inName - name of student
     * <p>
     * @param inStudinStudentID - student IDentID
     * <p>
     * @param inGender - gender of student
     * <p>
     * @param inGPA - gpa of student
     * <p>
     * @return Boolean success of operation
     */
    public boolean addItem(java.lang.String inName,
                       int inStudentID,
                       char inGender,
                       double inGPA)
    {
        // create object and call add item with object
        SimpleStudentClass newStudent = new SimpleStudentClass(inName, 
                inStudentID, inGender, inGPA );
        return addItem(newStudent);
    }
    /**
     * Adds item to hash table
     * <p>
     * Overloaded method that accepts SimpleStudentClass object
     * <p>
     * @param newItem - student class object
     * <p>
     * @return boolean success of operation
     */
    public boolean addItem(SimpleStudentClass newItem)
    { 
        appendToList(generateHash(newItem), newItem);
        return true;
    }
    /**
     * Appends new data to end of list at given list index; handles empty 
     * node at that index as needed
     * <p>
     * @param listIndex - specified integer index of array
     * <p>
     * @param newNode - SimpleStudentClass node to be appended to array/list 
     */
    private void appendToList(int listIndex,
                          SimpleStudentClass newNode)
    {
        // check for null node
        if( tableArray[listIndex] == null )
        {
            // finds last node
            tableArray[listIndex] = new LinkedNodeClass(newNode);
            
        }
        // else sets to empty node
        else
        {
            //System.out.println("entered correct block");
            LinkedNodeClass currentNode = tableArray[listIndex];
            while( currentNode.nextRef != null )
            {
                currentNode = currentNode.nextRef;
            }
            // appends to last node in list
            currentNode.nextRef = new LinkedNodeClass(newNode);
        }
    }
    /**
     * clears hash table
     */
    public void clearHashTable()
    {
        for(int index = 0; index < tableSize; index++)
        {
            tableArray[index] = null;
        }
    }
    /**
     * Method recursively counts number of nodes in a given linked list
     * <p>
     * @param workingRef - LinkedNodeClass reference used for recursion; 
     * initially set to head
     * <p>
     * @return integer number of nodes found
     */
    private int countNodes(LinkedHashClass.LinkedNodeClass workingRef)
    {
        if( workingRef == null )
        {
            return 0;
        }
        int count = 1;
        while( workingRef.nextRef != null )
        {
            count++;
            workingRef = workingRef.nextRef;
        }
        return count;
    }
    /**
     * Searches for item in hash table using student ID as key
     * <p>
     * @param studentID - used for requesting data
     * <p>
     * @return SimpleStudentClass object removed, or null if not found
     */
    public SimpleStudentClass findItem(int studentID)
    {
        int index = generateHash(new SimpleStudentClass
                                        (" ", studentID, ' ', 0));
        return accessLinkedData(index, studentID, SEARCH);
    }
    /**
     * Method uses student ID to generate hash value for use as index in hash table
     * <p>
     * Process sums the Unicode values of the student ID numbers converted to characters
     * @param studentData - SimpleStudentClass object from which hash value will be generated
     * <p>
     * @return integer hash value to be used as array index
     */
    public int generateHash(SimpleStudentClass studentData)
    {
        int digit;
        int sum = 0;
        int studentID = studentData.studentID;
        while( studentID > 0 )
        { 
            digit = studentID % 10;
            studentID = studentID / 10; 
            sum += ('0' + digit);
        }
        return sum % tableSize;
    }
    /**
     * Removes item from hash table, using student ID as key
     * <p>
     * @param studentID - used for requesting data
     * <p>
     * @return SimpleStudentClass object removed, or null if not found
     */
    public SimpleStudentClass removeItem(int studentID)
    {
        // get index to call with
        int index = generateHash(new SimpleStudentClass
                           (" ", studentID, ' ', 0));        
        return accessLinkedData(index, studentID, REMOVE);
    }
    /**
     * traverses through all array bins, finds lengths of each linked list,
     * then displays as table
     * <p>
     * Shows table of list lengths, then shows table size, then shows the
     * number of empty bins and the longest linked list of the set; adapts to
     * any size array

     */
    public void showHashTableStatus()
    {
        System.out.println("Array node report: ");
        System.out.print( "Index:    " );
        for( int index = 0; index < tableSize; index++)
        {
            System.out.print(index + "     ");
        }
        System.out.print("\n        ");
        for( int index = 0; index < tableSize; index++)
        {
            System.out.print("----- ");
        }
        System.out.print("\n          ");
        for( int index = 0; index < tableSize; index++)
        {
            System.out.print(countNodes(tableArray[index]) + "     ");
        }
        System.out.println("");
    }
}
