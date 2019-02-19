package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AsIntStream implements IntStream {

    private int[] data;

    private List<Operation> operationsPipeline = new ArrayList<>();

    private Sink currentSink;

    private AsIntStream() {
        // To Do
    }

    public static IntStream of(int... values) {
        AsIntStream stream = new AsIntStream();
        stream.data = values;
        stream.currentSink = new Sink(null);
        stream.currentSink.data = values;
        return stream;
    }

    @Override
    public Double average() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer max() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer min() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer sum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IntStream filter(IntPredicate predicate) {

        Sink sink = new Sink(currentSink) {

            @Override
            public boolean hasNext() {
                return super.hasNext();
            }

            @Override
            public Integer next() {
                return super.next();
            }
        };

        this.operationsPipeline.add(new Operation() {
            @Override
            int handle(int e) {
                System.out.println("Test e=" + e);
                if (predicate.test(e)) {
                    return e;
                } else {
                    this.setHasValue(false);
                    return 0;
                }
            }
        });
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        label: for (int i : data) {
            if (operationsPipeline.isEmpty()) {
                action.accept(i);
            } else {
                for(Operation oper : operationsPipeline) {
                    int el = oper.handle(i);
                    if (oper.hasValue()) {
                        action.accept(el);
                    } else {
                        oper.setHasValue(true);
                        continue label;
                    }
                }
            }
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        this.operationsPipeline.add(new Operation() {

            @Override
            int handle(int e) {
                int val = mapper.apply(e);
                System.out.println("Map=" + val + ", e=" + e);
                return val;
            }

        });
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {

        return null;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        label: for (int i : data) {
            System.out.println("El=" + i);
            int temp = 0;
            for(Operation oper : operationsPipeline) {
                int el = oper.handle(i);
                System.out.println("Handle: el=" + el + ", identity=" + identity + ", operation has value=" + oper.hasValue());
                if (oper.hasValue()) {
                    temp = op.apply(identity, el);
                } else {
                    oper.setHasValue(true);
                    continue label;
                }
            }
            identity = temp;
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        int[] tempArray = new int[data.length];
        int index = 0;
        label: for (int i : data) {
            if (operationsPipeline.isEmpty()) {
                return Arrays.copyOf(data, data.length);
            } else {
                for(; index < operationsPipeline.size(); index++) {
                    Operation oper = operationsPipeline.get(index);
                    int el = oper.handle(i);
                    if (oper.hasValue()) {
                        tempArray[index] = el;
                    } else {
                        oper.setHasValue(true);
                        continue label;
                    }
                }
            }
        }
        return Arrays.copyOf(tempArray, index);
    }

    private abstract static class Operation {
        private boolean hasValue = true;
        abstract int handle(int e);

        public boolean hasValue() {
            return hasValue;
        }

        public void setHasValue(boolean hasValue) {
            this.hasValue = hasValue;
        }
    }

    private abstract static class InnerStream extends AsIntStream {
        private IntStream downStream;

        private InnerStream(IntStream downStream) {
            this.downStream = downStream;
        }


    }

    private static class Sink implements Iterator<Integer> {

        private int[] data;
        private int elem;

        private Sink downStream;

        private Sink(Sink downStream) {
            this.downStream = downStream;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Integer next() {
            return null;
        }

        void accept(int i){
            elem = i;
        }

        public Sink getDownStream() {
            return downStream;
        }

        public int[] getData() {
            return data;
        }

        public void setData(int[] data) {
            this.data = data;
        }
    }

}
