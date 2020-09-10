package ru.newmcpe.bhack.util;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Ассинхронный инициализатор Map'a
 * @param <K> key
 * @param <V> value
 */
public class AsyncLoader<K, V> {

	private Map<K, Object> map;

	/**
	 * Ассинхронный инициализатор Map'a
	 * @param map map, который будем использовать для хранения данных
	 */
	@SuppressWarnings("unchecked")
	public AsyncLoader(Map<K, V> map) {
		this.map = (Map<K, Object>) map;
	}

	/**
	 * Загрузить данные
	 * @param key      ключ
	 * @param consumer обработка результата
	 * @param load     загрузка данных этого ключа
	 */
	@SuppressWarnings("unchecked")
	public void loadOrGet(K key, Consumer<V> consumer, Consumer<Consumer<V>> load) {
		Object o = map.get(key);
		if(o != null) {
			if(o instanceof Container) {
				Container<V> container = (Container) o;
				Consumer<V> previously = container.consumer;
				container.consumer = v -> {
					try {
						previously.accept(v);
					} catch(Exception e) {
						e.printStackTrace();
					}
					consumer.accept(v);
				};
			} else {
				consumer.accept((V) o);
			}
		} else {
			Container<V> container = new Container<>(key, consumer);
			map.put(key, container);
			load.accept(container);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<K, V> getMap() {
		return (Map<K, V>) map;
	}

	private class Container<VInner> implements Consumer<VInner> {

		private K key;
		private Consumer<VInner> consumer;
		private Consumer<VInner> consumerOriginal;

		Container(K key, Consumer<VInner> consumer) {
			this.key = key;
			this.consumer = this.consumerOriginal = consumer;
		}

		@Override
		public void accept(VInner v) {
			map.put(key, v);
			this.consumer.accept(v);
		}

		@Override
		public String toString() {
			return consumerOriginal + " at " + super.toString();
		}
	}
}
