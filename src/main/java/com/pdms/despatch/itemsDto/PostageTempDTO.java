/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.itemsDto;

import com.pdms.despatch.dto.PostageUpDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Phanivaranasi
 */
public class PostageTempDTO implements Serializable{
    
    private List<PostageUpDTO> postageDTO;
    private List<PostageItemsDTO> postageItemsDTO;

    public List<PostageUpDTO> getPostageDTO() {
        return postageDTO;
    }

    public List<PostageItemsDTO> getPostageItemsDTO() {
        return postageItemsDTO;
    }

    public void setPostageDTO(List<PostageUpDTO> postageDTO) {
        this.postageDTO = postageDTO;
    }

    public void setPostageItemsDTO(List<PostageItemsDTO> postageItemsDTO) {
        this.postageItemsDTO = postageItemsDTO;
    }
    
    
}
