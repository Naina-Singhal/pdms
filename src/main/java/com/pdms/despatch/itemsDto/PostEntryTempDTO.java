/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.itemsDto;

import com.pdms.despatch.dto.PostEntryDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Phanivaranasi
 */
public class PostEntryTempDTO implements Serializable{
    
    List<PostEntryDTO> postDTO;
    List<PostPoItemsDTO> postPoDTO;
    List<PostVenItemsDTO> postVenDTO;

    public List<PostPoItemsDTO> getPostPoDTO() {
        return postPoDTO;
    }

    public List<PostVenItemsDTO> getPostVenDTO() {
        return postVenDTO;
    }

    public void setPostPoDTO(List<PostPoItemsDTO> postPoDTO) {
        this.postPoDTO = postPoDTO;
    }

    public void setPostVenDTO(List<PostVenItemsDTO> postVenDTO) {
        this.postVenDTO = postVenDTO;
    }

    public List<PostEntryDTO> getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(List<PostEntryDTO> postDTO) {
        this.postDTO = postDTO;
    }
    
    
    
}
