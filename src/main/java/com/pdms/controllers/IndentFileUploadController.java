package com.pdms.controllers;

import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentFormFileDTO;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.OutputStream;
import static java.lang.StrictMath.log;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class IndentFileUploadController {

    private static final Logger log = Logger.getLogger(IndentFileUploadController.class);
    
    @Autowired
    private IndentServiceImpl indentService;

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;


    @RequestMapping(value = "/updfile")
    public ModelAndView UploadIndentFilesForm(HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();

        try {
            String encIndentID = request.getParameter("indid");
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID);

            modelView.addObject("indentSObj", indentObj);

            List<IndentFormFileDTO> indentFileLi = indentService.getAllIndentFiles(indentObj);
            modelView.addObject("indentFileLi", indentFileLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/indent/IndentFileUpload");

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "UploadIndentFilesForm :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/saveupdfile")
    public ModelAndView SaveUploadIndentFilesAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();

        try {

            int resVal = indentService.insertIndentFormUploadedFileDetail(indentObj, request);
            if (resVal > 0) {
                modelView.addObject("smsg", "File Uploaded Successfully.");
                //redirectAttributes.addFlashAttribute("indentFileLi", indentFileLi);
                //redirectAttributes.addFlashAttribute("indentSObj",indentObj);
                redirectAttributes.addFlashAttribute("smsg", "File Uploaded Successfully.");
            } else {
                modelView.addObject("ermsg", "Problem Occurred while Uploading File. Please contact Administrator!!");
                redirectAttributes.addFlashAttribute("ermsg", "Problem Occurred while Uploading File. Please contact Administrator!!");
            }
            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addAttribute("indid", indentObj.getEncFieldValue());

            modelView.setViewName("redirect:/updfile.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveUploadIndentFilesAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/delupdfile")
    public ModelAndView DeleteUploadIndentFileAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eid") String encIndId,
            @RequestParam("efd") String encFileId,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();

        try {

            int resVal = indentService.deleteIndentFormUploadedFileDetail(encIndId, encFileId);
            if (resVal > 0) {
                modelView.addObject("smsg", "File Deleted Successfully.");
                //redirectAttributes.addFlashAttribute("indentFileLi", indentFileLi);
                //redirectAttributes.addFlashAttribute("indentSObj",indentObj);
                redirectAttributes.addFlashAttribute("smsg", "File Deleted Successfully.");
            } else {
                modelView.addObject("ermsg", "Problem Occurred while Processing the File. Please contact Administrator!!");
                redirectAttributes.addFlashAttribute("ermsg", "Problem Occurred while Processing the File. Please contact Administrator!!");
            }
            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addAttribute("indid", encIndId);

            modelView.setViewName("redirect:/updfile.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveUploadIndentFilesAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/dndupdfile")
    public void DownloadIndentFile(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eid") String encIndId,
            @RequestParam("efd") String encFileId,
            RedirectAttributes redirectAttributes) {
        try {

            long indentID = 0;
            if (!org.apache.commons.lang.StringUtils.isEmpty(encIndId)) {
                String decIndentID = encryptDecrypt.decrypt(encIndId);
                if (NumberUtils.isNumber(decIndentID)) {
                    indentID = Long.parseLong(decIndentID);
                }
            }
            long fileUploadID = 0;
            if (!org.apache.commons.lang.StringUtils.isEmpty(encFileId)) {
                String decFileUploadID = encryptDecrypt.decrypt(encFileId);
                if (NumberUtils.isNumber(decFileUploadID)) {
                    fileUploadID = Long.parseLong(decFileUploadID);
                }
            }

            IndentFormFileDTO fileDTO = indentService.getSelectedIndentFileUploaded(indentID, fileUploadID);

            response.setContentType(fileDTO.getFileType());
            response.setHeader("Content-Disposition", "attachment; filename= \"" + fileDTO.getFileName() + "\"");
            File f = new File(fileDTO.getFilePath());
            FileInputStream fin = new FileInputStream(f);
            int size = fin.available();
            response.setContentLength(size);
            byte[] ab = new byte[size];
            OutputStream os = response.getOutputStream();
            int bytesread;
            do {
                bytesread = fin.read(ab, 0, size);
                if (bytesread > -1) {
                    os.write(ab, 0, bytesread);
                }
            } while (bytesread > -1);

            fin.close();
            os.flush();
            os.close();
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveUploadIndentFilesAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
    }

    
    @RequestMapping(value = "/apprindupdfile")
    public ModelAndView UploadIndentFilesInApproveIndentForm(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();

        try {
            String encIndentID = request.getParameter("indid");
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID.trim());

            modelView.addObject("indentSObj", indentObj);

            List<IndentFormFileDTO> indentFileLi = indentService.getAllIndentFiles(indentObj);
            modelView.addObject("indentFileLi", indentFileLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/indent/ApproveIndentFileUpload");

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "UploadIndentFilesInApproveIndentForm :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
    
    
    @RequestMapping(value = "/apprindsaveupdfile")
    public ModelAndView ApproveIndentSaveUploadFilesAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();

        try {

            int resVal = indentService.insertIndentFormUploadedFileDetail(indentObj, request);
            if (resVal > 0) {
                //modelView.addObject("smsg", "File Uploaded Successfully.");
                //redirectAttributes.addFlashAttribute("indentFileLi", indentFileLi);
                //redirectAttributes.addFlashAttribute("indentSObj",indentObj);
                redirectAttributes.addFlashAttribute("msg", "File Uploaded Successfully.");
            } else {
                //modelView.addObject("ermsg", "Problem Occurred while Uploading File. Please contact Administrator!!");
                redirectAttributes.addFlashAttribute("ermsg", "Problem Occurred while Uploading File. Please contact Administrator!!");
            }
            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addAttribute("eindi", indentObj.getEncFieldValue());

            modelView.setViewName("redirect:/appeditindent.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveUploadIndentFilesAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
    
    
    @RequestMapping(value = "/apprinddelupdfile")
    public ModelAndView ApproveIndentDeleteUploadFileAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eid") String encIndId,
            @RequestParam("efd") String encFileId,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();

        try {

            int resVal = indentService.deleteIndentFormUploadedFileDetail(encIndId, encFileId);
            if (resVal > 0) {
                //modelView.addObject("msg", "File Deleted Successfully.");
                //redirectAttributes.addFlashAttribute("indentFileLi", indentFileLi);
                //redirectAttributes.addFlashAttribute("indentSObj",indentObj);
                redirectAttributes.addFlashAttribute("msg", "File Deleted Successfully.");
            } else {
                //modelView.addObject("ermsg", "Problem Occurred while Processing the File. Please contact Administrator!!");
                redirectAttributes.addFlashAttribute("ermsg", "Problem Occurred while Processing the File. Please contact Administrator!!");
            }
            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addAttribute("eindi", encIndId);

            modelView.setViewName("redirect:/appeditindent.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveUploadIndentFilesAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
    
}
