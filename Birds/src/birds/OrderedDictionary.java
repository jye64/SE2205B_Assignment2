/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

/**
 *
 * @author alanye
 */
public class OrderedDictionary implements OrderedDictionaryADT{
    class Node{
        BirdRecord birdRecord;
        Node left;
        Node right;
        Node parent;
        
        public Node(BirdRecord record){
            this.birdRecord = record;
            left = null;
            right = null; 
            parent = null;
        }
    } // end class Node
    
    private Node root;
    
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        while(current!=null){
            if(current.birdRecord.getDataKey().compareTo(k)==0){
                return current.birdRecord;
            }else if (current.birdRecord.getDataKey().compareTo(k)==1){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        throw new DictionaryException("There is no record matches the given key"); 
    }

    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node newNode = new Node(r);
        if (root==null){
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while(true){
            parent = current;
            if (r.getDataKey().compareTo(current.birdRecord.getDataKey())==-1){
                current = current.left;
                if (current==null){
                    parent.left = newNode;
                    newNode.parent = parent;
                    return;
                }
            }else if(r.getDataKey().compareTo(current.birdRecord.getDataKey())==0){
                throw new DictionaryException("A BirdRecord with the same key is found.");  
            }else{
                current = current.right;
                if (current == null){
                    parent.right = newNode;
                    newNode.parent = parent;
                    return;
                }     
            }
        }  
    }
   
    
    @Override
    public void remove(DataKey k) throws DictionaryException {
        //NB: throw exception in deleteRec
        deleteRec(root,k);  
    }
    
    private Node deleteRec(Node root, DataKey key) throws DictionaryException
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;
 
        /* Otherwise, recur down the tree */
        if (key.compareTo(root.birdRecord.getDataKey())==-1)
            root.left = deleteRec(root.left, key);
        else if (key.compareTo(root.birdRecord.getDataKey())==1)
            root.right = deleteRec(root.right, key);
 
        // if key is same as root's key, then This is the node
        // to be deleted
        else
        {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
 
            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root = findMin(root.right);
 
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.birdRecord.getDataKey());
        }
        
        if(root==null)
            throw new DictionaryException("No such record key exists");
 
        return root;
    }

    

    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException {
        
        Node node = root, successor = null;
        while (node != null) {
            if (node.birdRecord.getDataKey().compareTo(k)==1) {
                successor = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return successor.birdRecord;
      
    }
    

    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException {
        
        Node node = root, predecessor = null;
        while(node!=null){
            if(node.birdRecord.getDataKey().compareTo(k)==-1){
                predecessor = node;
                node = node.right;
            }else{
                node = node.left;
            }
        }
        return predecessor.birdRecord;
   
    }
    

    @Override
    public BirdRecord smallest() throws DictionaryException {
        Node current = root;
        if (root==null){
            throw new DictionaryException("Dictionary is empty");
        }
        while (current.left!= null){
            current = current.left;
        }
        return current.birdRecord;
    }

    @Override
    public BirdRecord largest() throws DictionaryException {
        Node current = root;
        if (root==null){
            throw new DictionaryException("Dictionary is empty");
        }
        while(current.right != null){
            current = current.right;
        }
        return current.birdRecord;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
 
//>>>>>>>>>>> ADDED PRIVATE METHODS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   
    //find Node with DataKey k
    private Node findNodeWithKey(DataKey k) throws DictionaryException{
        Node current = root;
        while (root!=null){
            if(current.birdRecord.getDataKey().compareTo(k)==0){
                return current;
            }else if (current.birdRecord.getDataKey().compareTo(k)==1){
                current = current.left;
            }else {
                current = current.right;
            }
        }
        throw new DictionaryException("No Node with such key.");
    }
    
    //find the minimum in right subtree
    private Node findMin(Node root){
        if(root==null){
            return root;
        }
        while(root.left!=null){
            root = root.left;
        }
        return root;
    }
    
    //find the maximum in left subtree
    private Node findMax(Node root){
        if(root==null){
            return null;
        }
        while(root.right!=null){
            root = root.right;
        }
        return root;
    }
}

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        
