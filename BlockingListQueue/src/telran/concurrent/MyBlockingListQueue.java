package telran.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class MyBlockingListQueue<E> implements BlockingQueue<E> {

	private static final int DEFAULT_LIMIT = 10;
	
	public final int limit;
	private final Queue<E> queue = new LinkedList<>();
	private final Lock monitor = new ReentrantLock();
	private final Condition transmitterWait = monitor.newCondition();
	private final Condition reciverWait = monitor.newCondition();

	public MyBlockingListQueue() {
		this.limit = DEFAULT_LIMIT;
	}
	
	public MyBlockingListQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public E remove() {
		monitor.lock();
		try {
			E result = queue.remove();
			transmitterWait.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll() {
		monitor.lock();
		try {
			E result = queue.poll();
			transmitterWait.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		monitor.lock();
		try {
			if (queue.size() == limit) { throw new IllegalStateException(); }
			boolean result = queue.add(e);
			reciverWait.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		monitor.lock();
		try {
			boolean result = queue.offer(e);
			reciverWait.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		monitor.lock();
		try {
			while (queue.size() >= limit) { transmitterWait.await(); }
			queue.add(e);
			reciverWait.signal();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		monitor.lock();
		try {
			while (queue.size() == 0) { reciverWait.await(); }
			E result = queue.remove();
			transmitterWait.signal();
			return result;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E element() {
		return queue.element();
	}

	@Override
	public E peek() {
		return queue.peek();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	// MARK: DO NO NEED TO IMPLIMENT

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		return null;
	}

	@Override
	public int remainingCapacity() {
		return 0;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		return 0;
	}

}
