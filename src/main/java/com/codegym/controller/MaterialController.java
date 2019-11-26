package com.codegym.controller;

import com.codegym.model.Material;
import com.codegym.model.MaterialForm;
import com.codegym.model.Supplier;
import com.codegym.service.MaterialService;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Optional;

@Controller
public class MaterialController {

    @Autowired
    Environment env;

    @Autowired
    MaterialService materialService;

    @Autowired
    SupplierService supplierService;

    @ModelAttribute("suppliers")
    public Iterable<Supplier> suppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/materials")
    public ModelAndView listCustomers(@RequestParam("s") Optional<String> s, @PageableDefault(size = 5) Pageable pageable) {
        Page<Material> materials;
        if (s.isPresent()) {
            materials = materialService.findAllByNameContaining(s.get(), pageable);
        } else {
            materials = materialService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }

    @GetMapping("/create-material")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("material/create");
        modelAndView.addObject("material", new Material());
        return modelAndView;
    }

    @PostMapping("/create-material")
    public ModelAndView saveMaterial(@ModelAttribute("material") Material material) {
        materialService.save(material);
        ModelAndView modelAndView = new ModelAndView("material/create");
        modelAndView.addObject("material", new Material());
        modelAndView.addObject("message", "New material created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-material/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Material material = materialService.findById(id);
        if (material != null) {
            ModelAndView modelAndView = new ModelAndView("material/edit");
            modelAndView.addObject("material", material);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-material")
    public ModelAndView updateMaterial(@ModelAttribute("material") Material material) {
        materialService.save(material);
        ModelAndView modelAndView = new ModelAndView("material/edit");
        modelAndView.addObject("material", material);
        modelAndView.addObject("message", "Material Update successful");
        return modelAndView;
    }

    @GetMapping("/delete-material/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Material material = materialService.findById(id);
        if (material != null) {
            ModelAndView modelAndView = new ModelAndView("material/delete");
            modelAndView.addObject("material", material);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-material")
    public String deleteMaterial(@ModelAttribute("material") Material material) {
        materialService.remove(material.getId());
        return "redirect:materials";
    }

    @GetMapping("/searchBySupplier")
    public ModelAndView getMaterialBySupplier(@RequestParam("search") int supplierId, Pageable pageable){
//        Supplier supplier = supplierService.findbyId(Long.parseLong(search));
        Page<Material> materials ;
        if (supplierId==-1){
            materials = materialService.findAll(pageable);
        }else {
            Supplier supplier = supplierService.findbyId((long) supplierId);
            materials = materialService.findAllBySupplier(pageable,supplier);
        }
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials",materials);
        modelAndView.addObject("search",supplierId);
        return modelAndView;
    }

    @GetMapping("/sortByPriceAsc")
    public ModelAndView getMaterialSortByPriceAsc(Pageable pageable){
        Page<Material> materials = materialService.findAllByOrderByPriceAsc(pageable);
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials",materials);
        return modelAndView;
    }

    @GetMapping("/sortByPriceDesc")
    public ModelAndView getMaterialSortByPriceDesc(Pageable pageable){
        Page<Material> materials = materialService.findAllByOrderByPriceDesc(pageable);
        ModelAndView modelAndView = new ModelAndView("material/list");
        modelAndView.addObject("materials",materials);
        return modelAndView;
    }

    @GetMapping("/newMaterial")
    public ModelAndView getNewMaterialForm(){
        ModelAndView modelAndView = new ModelAndView("material/create");
        modelAndView.addObject("materialForm",new MaterialForm());
        modelAndView.addObject("suppliers",suppliers());
        return modelAndView;
    }

    @PostMapping("/newMaterial")
    public ModelAndView addNewMaterial(@ModelAttribute("materialForm") MaterialForm materialForm){
        doUploadMaterial(materialForm, new Material());
        return new ModelAndView("redirect:/material/materialList");
    }
    private void doUploadMaterial(MaterialForm materialForm, Material material){
        MultipartFile multipartFile = materialForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("uploadPath").toString();
        try{
            FileCopyUtils.copy(materialForm.getAvatar().getBytes(),new File(fileUpload+fileName));
        }catch (Exception e){
            e.printStackTrace();
        }
        material.setCode(materialForm.getCode());
        material.setName(materialForm.getName());
        material.setDescription(materialForm.getDescription());
        material.setAvatar(fileName);
        material.setImportDate(materialForm.getImportDate());
        material.setPrice(materialForm.getPrice());
        material.setSupplier(materialForm.getSupplier());
        material.setQuantity(materialForm.getQuantity());
        materialService.save(material);
    }


}
