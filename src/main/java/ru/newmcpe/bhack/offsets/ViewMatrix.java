package ru.newmcpe.bhack.offsets;


import ru.newmcpe.bhack.BHack;

public class ViewMatrix {

    private static ViewMatrix INSTANCE;

    private long matrixOffset;

    private float[][] matrix = new float[4][4];

    private ViewMatrix(long offset) {
        this.matrixOffset = offset;
    }

    public static ViewMatrix getInstance() {
        return INSTANCE == null ? INSTANCE = new ViewMatrix(ClientOffsets.INSTANCE.getDwViewMatrix()) : INSTANCE;
    }

    public float[][] getViewMatrix() {
        return this.matrix;
    }

    public void updateMatrix() {
        readMatrix();
    }

    private void readMatrix() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = BHack.INSTANCE.getClientDLL().read(count * 4, 4, true).getFloat(0);
                count++;
            }
        }
    }
}
