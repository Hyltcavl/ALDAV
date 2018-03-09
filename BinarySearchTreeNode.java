// Marie Topphem mato5183 och Teodor Englund teen1593


package alda.tree;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */


public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		if (this.data.compareTo(data) == 0) { // kollar om värdet är samma
			return false;
		} else if (this.data.compareTo(data) < 0) { // kollar om värdet är mindre
			if (this.right == null) { // lägger den till höger, om höger är null
				this.right = new BinarySearchTreeNode(data);
				return true;
			} else { // anropar högra noden
				return this.right.add(data);
			}

		} else if (this.data.compareTo(data) > 0) {
			if (this.left == null) { // lägger den till vänster, om vänster är null
				this.left = new BinarySearchTreeNode(data);
				return true;
			} else { // anropar vänstra noden
				return this.left.add(data);
			}
		}
		return false;

	}

	private T findMin(BinarySearchTreeNode <T> node) {
		
		 if (node == null){
			    return null;
			  }
			  if (node.left == null){
			    return node.data; // or whatever your method is
			  } else{
			    return findMin(node.left);
			  }
		
	}

	public BinarySearchTreeNode<T> remove(T data) {


		return remove(this, data);

	}

	private BinarySearchTreeNode<T> remove(BinarySearchTreeNode<T> node, T value) {
		if(node == null) {

			return node;

		}else {

			if(node.data.compareTo(value) < 0) {
				node.right = remove(node.right, value);
			} else if(node.data.compareTo(value) > 0) {
				node.left = remove(node.left, value);
			} else if(node.data.compareTo(value) == 0){
				if(node.left == null && node.right == null) {
					return node = null;
				}else if(node.right == null) {
					return node.left;
				}else if(node.left == null) {
					return node.right;
				} else if(node.left != null && node.right != null) {
					
					BinarySearchTreeNode <T> temp = node.right;
					node.data = findMin(temp);
				    node.right = remove( node.right, node.data );
				}
			}
		}
		return node;
	}

	public boolean contains(T data) {

		if(this.data == null) {
			throw new NullPointerException();

		}else {
			if(this.data.compareTo(data) == 0) {
				return true;
			} else if((this.data.compareTo(data)) < 0 && (right != null)) {
				return right.contains(data);
			} else if((this.data.compareTo(data)) > 0 && (left != null )){
				return left.contains(data);
			}
			return false;
		}

	}

	public int size() {

		return size(this);
	}

	private int size(BinarySearchTreeNode<T> data) {
		if(data == null) {
			return 0;
		}else {
			return(size(data.left) + 1 + size(data.right));
		}
	}


	public int depth() {

		return(depth(this));
	}

	private int depth(BinarySearchTreeNode<T> node) {

		if(node.data== null) {
			return 0;
		}else if(node.left == null && node.right == null) {
			return 1;
		}else if(node.left== null){
			return depth(node.right);
		}else if(node.right== null){
			return depth(node.left);
		}
		return Math.max(depth(node.left),
				depth(node.right)) + 1;
	}

	public String toString() {

		String str = toString(this);

		return str.substring(0, str.length() -2 );
	}

	private String toString(BinarySearchTreeNode<T> node) {

		if(node == null) {
			return "";
		}else {
			return (node.toString(node.left) + node.data + ", " + node.toString(node.right));
		}

	}
}


