 public Color getPixel(BufferedImage image, int x, int y) {
    ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() *
      image.getHeight() * 4); //4 for RGBA, 3 for RGB

    if (y <= image.getHeight() && x <= image.getWidth()){
      int pixel = pixels[y * image.getWidth() + x];
      int r=(pixel >> 16) & 0xFF);     // Red 
      int g=(pixel >> 8) & 0xFF);      // Green 
      int b=(pixel & 0xFF);            // Blue
      int a=(pixel >> 24) & 0xFF);     // Alpha
      return new Color(r,g,b,a)
    }
    else{
      return new Color(0,0,0,1);
    }
}










//--------------------------------------------------------------------------------
byte[] pixels = new byte[size.x * size.y * 4];
ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length);
glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
buffer.get(pixels);

public Color getPixel(int x, int y) {
    if (x > size.x || y > size.y) {
        return null;
    }

    int index = (x + y * size.x) * 4;

    int r = pixels[index] & 0xFF;
    int g = pixels[index + 1] & 0xFF;
    int b = pixels[index + 2] & 0xFF;
    int a = pixels[index + 3] & 0xFF;

    return new Color(r, g, b, a);
}
