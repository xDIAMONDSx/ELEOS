package my.game.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EventBusser {
	
	public ConcurrentHashMap<String, ArrayList<IEventListener>> listeners = new ConcurrentHashMap<String, ArrayList<IEventListener>>();
	
	public EventBusser() {}
	
	/**
	 * Invokes all listeners of a specific event
	 * 
	 * @param e The event to invoke the handlers
	 */
	public void postToAllListeners(Event e) {
		try {
			ArrayList<IEventListener> eListeners = listeners.get(e.getClass().getName());
			if(eListeners == null) return;
			for (IEventListener listen : eListeners) {
				listen.invoke(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Registers a listener
	 * 
	 * @param handler The class containing event handlers
	 */
	public void registerListener(Object handler) {
		List<Class<?>> supers = new ArrayList<Class<?>>();
		supers.add(handler.getClass());
		Class<?> superCls = handler.getClass().getSuperclass();
		while (true) {
			supers.add(superCls);
			superCls = superCls.getClass().getSuperclass();
			if(superCls instanceof Object) break;
		}

		for (Method method : handler.getClass().getMethods()) {
			for (Class<?> cls : supers) {
				try {
					Method eventMethod = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
					if (eventMethod.isAnnotationPresent(EventHandler.class)) {
						Class<?>[] parameterTypes = method.getParameterTypes();
						if (parameterTypes.length != 1) {
							System.err.println("Method " + method + " has @EventHandler annotation, but it takes more than one argument"); 
						return;
						}

						Class<?> eventType = parameterTypes[0];

						if (!Event.class.isAssignableFrom(eventType)) { System.err.println("Method "
								+ method + " has @EventHandler annotation, but takes a argument that is not an Event "
								+ eventType); 
						}
						/*
						 * Register: 
						 * get the eventType (<-) 
						 * get the handler (target) 
						 * get the method (<-) 
						 * send to eventType
						 */
						
						//In Generic Event Listener, Implement IEventListener, invoke stuff
						GenericEventListener l = new GenericEventListener(method, handler);
						l.setPriority(eventMethod.getAnnotation(EventHandler.class).value());
						
						ArrayList<IEventListener> otherHandlers = listeners.get(eventType.getName());
						if(otherHandlers == null) {
							otherHandlers = new ArrayList<IEventListener>();
							otherHandlers.add(l);
							listeners.put(eventType.getName(), otherHandlers);
						}else {
							otherHandlers.add(l);
							otherHandlers.sort(new Comparator<IEventListener>() {

								@Override
								public int compare(IEventListener one, IEventListener two) {
									return one.getPriority().compareTo(two.getPriority());
								}
								
							});
							listeners.put(eventType.getName(), otherHandlers);
						}
						break;
					}
				} catch (NoSuchMethodException e) {
				
				} catch(Exception e) {
					System.err.println("____________________________________________________");
					System.err.println("OOPS!");
					e.printStackTrace();
					System.err.println("Culprit: "+handler.toString());
					//System.err.println("____________________________________________________");
				}
			}
		}
	}
	
	/**
	 * Registers a listener
	 * 
	 * @param evtClass The Event the listener handles
	 * @param listener The listener object
	 * @param priority The priority of the listener
	 */
	public void registerListener(Class<? extends Event> evtClass, IEventListener listener, EventHandler priority) {
		ArrayList<IEventListener> otherHandlers = listeners.get(evtClass.getName());
		if(otherHandlers == null) {
			otherHandlers = new ArrayList<IEventListener>();
			otherHandlers.add(listener);
			listeners.put(evtClass.getName(), otherHandlers);
		}else {
			otherHandlers.add(listener);
			otherHandlers.sort(new Comparator<IEventListener>() {

				@Override
				public int compare(IEventListener one, IEventListener two) {
					return one.getPriority().compareTo(two.getPriority());
				}
				
			});
			listeners.put(evtClass.getName(), otherHandlers);
		}
	}
	
}
