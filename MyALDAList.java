// Marie Topphem marietopphem@gmail.com	och Teodor Englund teodor.englund@gmail.com


package alda.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<E> implements ALDAList<E>{

	private static class Node<T> {
		T data;
		Node next;
		public Node(T data) {
			this.data = data;
		}

		public String toString() {
			return (String)data;
		}
	}

	private Node<E> first;
	private Node<E> last;


	@Override
	public Iterator iterator() {

		Iterator<E> myIterator = new Iterator<E>() {


			Node<E> current; 

			Node<E> previous;

			boolean hasRemoved = true;

			@Override
			public boolean hasNext() {

				if(current == null && previous == null) {
					if(first != null) {
						return true;
					}


				}else if(current.next != null) {
					return true;
				}

				return false;

			}

			@Override
			public E next() {

				if( !hasNext() ) {
					throw new NoSuchElementException();
				}

				if(hasNext() && current == null) {
					current = first;
					hasRemoved = false;

					return (E) current.data;


				}else if(hasNext()) {
					previous = current;
					current = previous.next;
					hasRemoved = false;
					return (E)current.data;
				}

				throw new NoSuchElementException();

			}

			@Override
			public void remove() {

				if(hasRemoved == false) {

					if(current == null) {
						throw new NoSuchElementException();
					}else if(current == first) {

						if(hasNext()) {
							first = first.next;

						}else {
							current = null;
							first = null;
						}
					}else {
						previous.next = current.next;
						current = previous;
					}
					
					hasRemoved = true;
				}else {
					throw new IllegalStateException();
				}

			}

		};

		return myIterator;
	}

	@Override
	public void add(E element) {
		// TODO Auto-generated method stub
		if (first == null) {
			first = new Node<E>(element);
			last = first;
		} else {
			last.next = new Node<E>(element);
			last = last.next;
			last.next = null;
		}
	}

	@Override
	public void add(int index, E element) {
		if( index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}else {

			if(index == 0) {

				Node <E> nodeTemp = first;
				first = new Node<E>(element);
				first.next = nodeTemp;
				if(last==null) {
					last = first;
				}

			}else if(index == size()) {
				last.next = new Node<E>(element);
				last = last.next;

			}else {
				int counter = 0;
				for(Node<E> temp=first; temp!=null; temp=temp.next) {

					if(counter==(index-1)) {
						Node newNode = new Node<E>(element);
						newNode.next = temp.next;
						temp.next = newNode;
						break;
					}
					counter++;
				}
			}
		}
	}

	@Override
	public E remove(int index) {
		if(index < 0 || index >= size() || size() == 0) {
			throw new IndexOutOfBoundsException();
		}

		if(index == 0) {

			Node<E> removeNode = first;
			first = first.next;

			if(first == null) {
				last = null;
			}


			return removeNode.data;	

		}else if(index == size()-1) {
			int counter = 0;
			for(Node<E> temp=first; temp!=null; temp=temp.next) {

				if(counter == index-1) {

					Node<E> removeNode = last;
					last = temp;
					last.next = null;

					return removeNode.data;
				}
				counter++;

			}			
		}else {
			int counter = 0;
			for(Node<E> temp=first; temp!=null; temp=temp.next) {
				if(counter== index-1) {

					Node<E> removeNode = temp.next;
					temp.next = removeNode.next;

					return removeNode.data;

				}

				counter++;
			}
		}

		return null;
	}

	@Override
	public boolean remove(E element) {

		if(size()==0) {
			return false;
		}else if(contains(element)) {
			if(first.data.equals(element)) {
				if(size()!=1){
					first = first.next;
					return true;
				}else {
					first = null;
					last = null;
				}

			}else{

				for(Node<E> temp=first; temp!=null; temp=temp.next) {
					if(last.data.equals(element) && temp.next == last) {

						last = temp;
						last.next= null;


						return true;
					}else if(temp.next.data.equals(element)) {

						temp.next = temp.next.next;

						return true;
					}

				}
			}
		}

		return false;
	}

	@Override
	public E get(int index) {
		int counter = 0;

		if(size() == 0 || size() <= index || index < 0 ) {
			throw new IndexOutOfBoundsException();
		}else {
			for(Node<E> temp=first; temp!=null; temp=temp.next) {
				if(counter == index) {
					return (E) temp.data;					
				}
				counter++;
			}
		}
		return null;

		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(E element) {

		for(Node<E> temp=first; temp!=null; temp=temp.next) {
			if(temp.data.equals(element) || temp == element) {
				return true;
			}

		}

		return false;
	}

	@Override
	public int indexOf(E element) {
		int count = 0;
		for(Node<E> temp=first; temp!=null; temp=temp.next) {
			if(temp.toString() == element) {
				return count;
			}

			count++;
		}

		return -1;
	}

	@Override
	public void clear() {

		first = null;
		last = null;

	}

	@Override
	public int size() {
		int size = 0;
		for(Node<E> temp=first; temp!=null; temp=temp.next) {
			size++;
		}

		return size;
	}

	public String toString() {
		String counter = "[";
		int count = 0;
		for(Node<E> temp=first; temp!=null; temp=temp.next) {
			if(count == 0) {
				counter+=temp;

			}else{
				counter += ", "+ temp.toString();
			}
			count++;
		}
		return counter + "]";


	}
}