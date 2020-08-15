package project.ui;

import io.reactivex.subjects.BehaviorSubject;

public final class RxBus {

    private static final BehaviorSubject<Object> behaviorSubject
            = BehaviorSubject.create();


    public static BehaviorSubject<Object> getSubject() {
        return behaviorSubject;
    }

    //reciever
    /*
        private void getSubjectData() {
        disposable = RxBus.getSubject().
                subscribeWith(new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object object) {
                        if (object instanceof String) {
                            chooseFragment(object.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
     */
    //sender
    /*
    RxBus.getSubject().onNext("Home") ;
     */
}