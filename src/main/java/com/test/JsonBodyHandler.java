package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyHandler<T> implements BodyHandler<Supplier<T>> {

    private TypeReference<T> targetClass;

    public JsonBodyHandler(TypeReference<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public BodySubscriber<Supplier<T>> apply(ResponseInfo responseInfo) {
        return this.toJson(this.targetClass);

    }

    public <W> BodySubscriber<Supplier<W>> toJson(TypeReference<W> targetClass) {
        BodySubscriber<InputStream> upstream = BodySubscribers.ofInputStream();

        return BodySubscribers.mapping(
                upstream,
                (body) -> this.toSupplierOfType(body, targetClass));
    }

    //returns arrow function which converts inputStream to json object
    public <W> Supplier<W> toSupplierOfType(InputStream inputStream, TypeReference<W> targetClass) {
        return () -> {
            try (InputStream stream = inputStream) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(stream, targetClass);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

}
