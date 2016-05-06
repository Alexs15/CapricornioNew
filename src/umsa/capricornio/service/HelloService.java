/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.service;

/**
 *
 * @author leo
 */
import java.io.InputStream;
public interface HelloService {
     String upload(String filename, InputStream data);
}
