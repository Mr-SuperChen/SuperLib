package superlib.cjt.co.openlibrary.rxbus;



/**
 * Created by Luhao on 2016/5/28.
 * rxbus的核心类
 */
public class RxBus {


//    private static volatile RxBus mInstance;
//    private final Subject<Object> subject = PublishSubject.create().toSerialized();
//    private Disposable dispoable;


    private RxBus() {
    }

//    public static RxBus getDefault() {
//        if (mInstance == null) {
//            synchronized (RxBus.class) {
//                if (mInstance == null) {
//                    mInstance = new RxBus();
//                }
//            }
//        }
//        return mInstance;
//    }


//    /**
//     * 发送事件
//     * @param object
//     */
//    public void post(Object object) {
//        subject.onNext(object);
//    }

//
//    /**
//     * @param classType
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> toObservale(Class<T> classType) {
//        return subject.ofType(classType);
//    }


//    /**
//     * 订阅
//     * @param bean
//     * @param consumer
//     */
//    public void subscribe(Class bean, Consumer consumer) {
//        dispoable = toObservale(bean).subscribe(consumer);
//    }

//    /**
//     * 取消订阅
//     */
//    public void unSubcribe(){
//        if (dispoable != null && dispoable.isDisposed()){
//            dispoable.dispose();
//        }
//
//    }

}
