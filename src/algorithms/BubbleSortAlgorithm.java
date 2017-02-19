package algorithms;

import models.DataSetModel;

import java.util.LinkedList;

public class BubbleSortAlgorithm extends Algorithm {
    public BubbleSortAlgorithm(DataSetModel dataSet) {
        super(dataSet);

        AlgorithmWorker algorithmWorker = new AlgorithmWorker();
        workerThread = new Thread(algorithmWorker);
        workerThread.start();
    }

    @Override
    public boolean nextStep() {
        synchronized (workerLock) {
            workerLock.notify();
            return !dataSet.getIsSorted();
        }
    }

    private class AlgorithmWorker implements Runnable {
        @Override
        public void run() {
            LinkedList<Integer> data = dataSet.getData();

            try {
                bubbleSort(data);

                synchronized (workerLock) {
                    workerLock.wait();

                    dataSet.setIsSorted();
                }
            } catch (InterruptedException e) {
                System.out.println("BubbleSort has been destroyed.");
            }
        }

        private void bubbleSort(LinkedList<Integer> numbers) throws InterruptedException {
            boolean swapped;
            int size = numbers.size();

            do {
                swapped = false;
                for (int i = 1; i < size; i++) {
                    synchronized (workerLock) {
                        workerLock.wait();
                        dataSet.markComparedNumbers(i - 1, i);

                        if (numbers.get(i - 1) > numbers.get(i)) {
                            dataSet.swap(i - 1, i);
                            swapped = true;
                        }
                    }
                }
            } while (swapped);
        }
    }
}
