/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.IssueVoucherDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class IssueVoucherTempDTO implements Serializable{
    
    private List<IssueVoucherDTO> issueDTO;
    private List<IssueVouItemsDTO> issueItemsDTO;

    public List<IssueVoucherDTO> getIssueDTO() {
        return issueDTO;
    }

    public List<IssueVouItemsDTO> getIssueItemsDTO() {
        return issueItemsDTO;
    }

    public void setIssueDTO(List<IssueVoucherDTO> issueDTO) {
        this.issueDTO = issueDTO;
    }

    public void setIssueItemsDTO(List<IssueVouItemsDTO> issueItemsDTO) {
        this.issueItemsDTO = issueItemsDTO;
    }
    
    
    
}
