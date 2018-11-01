/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execução;

import java.util.List;

/**
 *
 * @author udesc
 */
public interface IMainView {
   public List<String> getListFiles();
   public void setTextResultValue(String values);
   public String getTextResultValue();
   public String getTextWordToFind();
}
