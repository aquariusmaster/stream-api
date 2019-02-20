package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsIntStream implements IntStream {

    private int[] data;

    private List<Operation> operationsPipeline;

    private AsIntStream() {
        operationsPipeline = new ArrayList<>();
    }

    public static IntStream of(int... values) {
        AsIntStream stream = new AsIntStream();
        stream.data = values;
        return stream;
    }

    @Override
    public Double average() {
        return (double) sum() / count();
    }

    @Override
    public Integer max() {
        return reduce(Integer.MIN_VALUE, Math::max);
    }

    @Override
    public Integer min() {
        return reduce(Integer.MAX_VALUE, Math::min);
    }

    @Override
    public long count() {
        return toArray().length;
    }

    @Override
    public Integer sum() {
        return reduce(0, (l, r) -> l + r);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        this.operationsPipeline.add(new Operation() {
            @Override
            int handle(int e) {
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
        label:
        for (int i : data) {
            if (operationsPipeline.isEmpty()) {
                action.accept(i);
            } else {
                for (Operation oper : operationsPipeline) {
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
                return mapper.apply(e);
            }
        });
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        label:
        for (int i : data) {
            if (operationsPipeline.isEmpty()) {
                identity = op.apply(identity, i);
            } else {
                int temp = 0;
                for (Operation oper : operationsPipeline) {
                    int el = oper.handle(i);
                    if (oper.hasValue()) {
                        temp = op.apply(identity, el);
                    } else {
                        oper.setHasValue(true);
                        continue label;
                    }
                }
                identity = temp;
            }
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        int[] tempArray = new int[data.length];
        int index = 0;
        label:
        for (int i : data) {
            if (operationsPipeline.isEmpty()) {
                return Arrays.copyOf(data, data.length);
            } else {
                for (; index < operationsPipeline.size(); index++) {
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


}
