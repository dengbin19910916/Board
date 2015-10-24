package com.wrox.utils.excel.adapter;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by dengb on 2015/10/20.
 */
public class MultisetTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        if (typeToken.getRawType() != Multiset.class || !(type instanceof ParameterizedType)) {
            return null;
        }

        Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
        TypeAdapter<?> elementAdapter = gson.getAdapter(TypeToken.get(elementType));
        return (TypeAdapter<T>) newMultisetAdapter(elementAdapter);
    }

    private <E> TypeAdapter<Multiset<E>> newMultisetAdapter(final TypeAdapter<E> elementAdapter) {
        return new TypeAdapter<Multiset<E>>() {
            public void write(JsonWriter out, Multiset<E> value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }

                out.beginArray();
                for (Multiset.Entry<E> entry : value.entrySet()) {
                    out.value(entry.getCount());
                    elementAdapter.write(out, entry.getElement());
                }
                out.endArray();
            }

            public Multiset<E> read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }

                Multiset<E> result = LinkedHashMultiset.create();
                in.beginArray();
                while (in.hasNext()) {
                    int count = in.nextInt();
                    E element = elementAdapter.read(in);
                    result.add(element, count);
                }
                in.endArray();
                return result;
            }
        };
    }
}
