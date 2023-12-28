package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.ItemStream;

public class LimitedItemReader<T> implements ItemReader<T>, ItemStream {

    private final FlatFileItemReader<T> delegate;
    private int count = 0;
    private final int limit;

    public LimitedItemReader(FlatFileItemReader<T> delegate, int limit) {
        this.delegate = delegate;
        this.limit = limit;
    }

    @Override
    public T read() throws Exception {
        if (count < limit) {
            count++;
            return delegate.read();
        } else {
            return null;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
