package nTEngine.Util;

import nTEngine.gameObj.nTImageMatrix;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import nTEngine.gameObj.nTMesh;
import nTEngine.gameObj.nTTexture;
import nTEngine.nTGL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import org.newdawn.slick.TrueTypeFont;

public class nTLoader {
    public static String loadText(String fileName) throws Exception {
        String result = "";
        try (InputStream in = new FileInputStream(fileName)) {
            if(in==null){
                System.out.println("Error loading: "+fileName);
                return null;
            }
            result = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        }
        return result;
    }
    
    public static List<String> readAllLines(String fileName) throws Exception {
        List<String>list=new ArrayList<>();
        try (BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            if(in==null){
                System.out.println("Error loading: "+fileName);
                return null;
            }
            String line;
            while ((line=in.readLine())!=null){
                list.add(line);
            }
        }
        return list;
    }
    
    public static nTTexture loadTexture(String fileName) throws Exception {
        InputStream in=new FileInputStream(fileName);
        if(in==null){
            System.out.println("Error loading: "+fileName);
            return null;
        }
        PNGDecoder decoder = new PNGDecoder(in);
        ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
        decoder.decode(buf,decoder.getWidth()*4,Format.RGBA);
        buf.flip();
        int id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,decoder.getWidth(),decoder.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,buf);
        glGenerateMipmap(GL_TEXTURE_2D);//cria imagens de resolucao menor pra usar automaticamente quando o objeto diminuir
        return new nTTexture(id,decoder.getWidth(),decoder.getHeight());
    }
    
    public static TrueTypeFont loadFont(String fileName,float size) throws FileNotFoundException{
        InputStream in = new FileInputStream(fileName);
        TrueTypeFont font=null;
        Font awtFont=null;
        try {
            awtFont = Font.createFont(Font.TRUETYPE_FONT, in);
            awtFont = awtFont.deriveFont(size);
            font = new TrueTypeFont(awtFont, nTGL.fontAntiAlias);
        } catch (FontFormatException | IOException e) {
        }   
        return font;
    }
    
    public static nTImageMatrix loadTextureMatrix(String fileName) throws Exception {
        InputStream in=new FileInputStream(fileName);
        if(in==null){
                System.out.println("Error loading: "+fileName);
                return null;
        }
        PNGDecoder decoder = new PNGDecoder(in);
        ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
        decoder.decode(buf,decoder.getWidth()*4,Format.RGBA);
        buf.flip();
        return new nTImageMatrix(buf,decoder.getWidth(),decoder.getHeight());
    } 
    
    public static nTMesh loadObj(String fileName) throws Exception {
        List<String> lines = readAllLines(fileName);
        List<nTPoint>vertices=new ArrayList<>();
        List<nTPoint>textures=new ArrayList<>();
        List<nTPoint>normals=new ArrayList<>();
        List<nTFace>faces=new ArrayList<>();
        for(String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v"://ponto
                    nTPoint point=new nTPoint(Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    vertices.add(point);
                break;
                case "vt"://texture Cord
                    nTPoint texCord=new nTPoint(Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));
                    textures.add(texCord);
                break;
                case "vn"://normal
                    nTPoint normal=new nTPoint(Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    normals.add(normal);
                break;
                case "f"://face TODO: permitir faces com n pontos
                    nTFace face=new nTFace(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    private static nTMesh reorderLists(List<nTPoint>posList,List<nTPoint>textCoordList,List<nTPoint>normList,List<nTFace>facesList){
        List<Integer>indices=new ArrayList();
        float[]positions=new float[posList.size()*3];
        int i=0;
        for(nTPoint pos : posList){
            positions[i*3]=(float)pos.x;
            positions[i*3+1]=(float)pos.y;
            positions[i*3+2]=(float)pos.z;
            i++;
        }
        float[]texCords=new float[posList.size()*2];
        float[]normals=new float[posList.size()*3];

        for(nTFace face : facesList){
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for(IdxGroup indValue : faceVertexIndices){
                processFaceVertex(indValue,textCoordList,normList,indices,texCords,normals);
            }
        }
        int[] indicesVector=new int[indices.size()];
        indicesVector=indices.stream().mapToInt((Integer v) -> v).toArray();
        nTMesh mesh=new nTMesh(positions, texCords, normals, indicesVector);
        return mesh;
    }

    static void processFaceVertex(IdxGroup indices,List<nTPoint>textCoordList,List<nTPoint>normList,List<Integer>indicesList,float[]texCoordArr,float[]normArr){
        int posIndex=indices.idxPos;
        indicesList.add(posIndex);
        if(indices.idxTextCoord>=0) {
            nTPoint textCoord=textCoordList.get(indices.idxTextCoord);
            texCoordArr[posIndex*2]=(float) textCoord.x;
            texCoordArr[posIndex*2+1]=(float) (1-textCoord.y);
        }
        if(indices.idxVecNormal>=0){
            nTPoint normal=normList.get(indices.idxVecNormal);
            normArr[posIndex*3]=(float) normal.x;
            normArr[posIndex*3+1]=(float) normal.y;
            normArr[posIndex*3+2]=(float) normal.z;
        }
    }
    
    private static class nTFace {
        private IdxGroup[] idxGroups = new IdxGroup[3];

        public nTFace(String v1, String v2, String v3) {
            idxGroups = new IdxGroup[3];
            idxGroups[0] = parseLine(v1);
            idxGroups[1] = parseLine(v2);
            idxGroups[2] = parseLine(v3);
        }
        private IdxGroup parseLine(String line) {
            IdxGroup idxGroup=new IdxGroup();
            String[] lineTokens=line.split("/");
            int length=lineTokens.length;
            idxGroup.idxPos=Integer.parseInt(lineTokens[0]) - 1;
            if(length>1){
                String textCoord=lineTokens[1];
                idxGroup.idxTextCoord=textCoord.length() > 0 ? Integer.parseInt(textCoord)-1:-1;
                if(length>2){
                    idxGroup.idxVecNormal=Integer.parseInt(lineTokens[2]) - 1;
                }
            }
            return idxGroup;
        }
        public IdxGroup[] getFaceVertexIndices() {
            return idxGroups;
        }
    }
    private static class IdxGroup {
        public int idxPos;
        public int idxTextCoord;
        public int idxVecNormal;
        public IdxGroup() {
            idxPos=-1;
            idxTextCoord=-1;
            idxVecNormal=-1;
        }
    }
    
}
