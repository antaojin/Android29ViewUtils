package com.example.viewutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {
	public static void inject(Activity activity){
		bindView(activity);
		bindOnClick(activity);
	}

	private static void bindView(Activity activity) {
		/*
		 * 1. 获取Activity的字节码
		 */
		Class clazz = activity.getClass();
		/*
		 * 2. 通过字节码获取所有的Field
		 */
		Field[] fields = clazz.getDeclaredFields();
		/*
		 * 3. 遍历Field，获取Field上面的ViewInject的自定义注解
		 */
		for(int i=0;i< fields.length; i++){
			Field field = fields[i];
			//获取Filed头上的注解，如果为null则该字段不是我们想要
			/*
			 * 4. 获取自定义注解对象，然后获取到id
			 */
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject!=null) {
				int resId = viewInject.value();
				/*
				 * 5.View view = activity.findViewById(id)
				 */
				View view = activity.findViewById(resId);
				/*
				 * 6. 将View对象暴力反射给Field
				 */
				field.setAccessible(true);
				try {
					//暴力反射不需要强转
					field.set(activity, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
	}

	private static void bindOnClick(final Activity activity) {
		/*
		 * 1. 获取Activity的字节码
		 */
		Class clazz = activity.getClass();
		/*
		 * 2. 获取所有的方法
		 */
		Method[] methods = clazz.getDeclaredMethods();
		/*
		 * 3. 遍历方法，Method只处理有OnClick注解的方法
		 */
		for(final Method method : methods){
			/*
			 * 4. 获取方法上面的注解，并获取其id
			 */
			OnClick onClick = method.getAnnotation(OnClick.class);
			if (onClick!=null) {
				int resId = onClick.value();
				/*
				 * 5. View view = activity.findViewById(id)
				 */
				final View view = activity.findViewById(resId);
				/*
				 * 6. 给View绑定点击监听事件 view.setOnClikListener(new OnClickListener(){
				 * 	public void onclick(View view){
				 * 				//todo
				 * }
				 * });
				 */
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						/*
						 * 7. 在onClick中反射调用Method方法
						 */
						method.setAccessible(true);
						try {
							method.invoke(activity, view);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				});
				
			}
		}
		
	}
}
