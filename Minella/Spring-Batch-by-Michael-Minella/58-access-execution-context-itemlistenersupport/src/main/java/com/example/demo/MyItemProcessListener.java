package com.example.demo;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

import java.util.ArrayList;
import java.util.List;

/**
 * Marker interface defining a contract for periodically storing state and restoring from that state should an 
 * error occur.
 *
 */

class MyItemProcessListener implements ItemProcessListener<Integer, Integer>, ItemStream {

        private ExecutionContext executionContext;

        @Override
        public void open(ExecutionContext executionContext) throws ItemStreamException {
            this.executionContext = executionContext;
            this.executionContext.put("errorItems", new ArrayList<Integer>());
        }

        @Override
        public void update(ExecutionContext executionContext) throws ItemStreamException {
        }

        @Override
        public void close() throws ItemStreamException {
        }

        @Override
        public void beforeProcess(Integer item) {
            
        }

        @Override
        public void afterProcess(Integer item, Integer result) {

        }

        @Override
        @SuppressWarnings("unchecked")
        public void onProcessError(Integer item, Exception e) {
            List<Integer> errorItems = (List<Integer>) executionContext.get("errorItems");
            errorItems.add(item);
            executionContext.put("errorItems", errorItems);
        }
    }