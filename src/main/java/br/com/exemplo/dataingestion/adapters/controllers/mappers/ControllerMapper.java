package br.com.exemplo.dataingestion.adapters.controllers.mappers;

public interface ControllerMapper<I, O> {
    public O convert(I i);
}
