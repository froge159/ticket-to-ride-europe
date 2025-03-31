package utils;

enum ColorEnum {

    YELLOW(255, 255, 0),
    BLUE(65, 105, 225),
    GREEN(0, 128, 0),
    RED(178, 34, 34);

    private final int[] rgb;

    private ColorEnum(int v1, int v2, int v3) {
        rgb = new int[3];
        rgb[0] = v1; rgb[1] = v2; rgb[2] = v3;
    }

    public int[] getRGB(){
        return rgb;
    }

}