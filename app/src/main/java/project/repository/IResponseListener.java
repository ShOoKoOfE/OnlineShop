package project.repository;

public interface IResponseListener<T> {
    void OnResponseComplete(T result);
}