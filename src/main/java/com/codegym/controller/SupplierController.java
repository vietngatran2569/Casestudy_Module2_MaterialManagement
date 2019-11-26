package com.codegym.controller;

import com.codegym.model.Material;
import com.codegym.model.Supplier;
import com.codegym.service.MaterialService;
import com.codegym.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/suppliers")
    public ModelAndView listSupplier(){
        Iterable<Supplier> suppliers=supplierService.findAll();
        ModelAndView modelAndView=new ModelAndView("material/supplier/list");
        modelAndView.addObject("suppliers",suppliers);
        return modelAndView;
    }
    @GetMapping("/create-supplier")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("material/supplier/create","supplier",new Supplier());
        return modelAndView;
    }
    @PostMapping("/create-supplier")
    public ModelAndView saveSupplier(@ModelAttribute("supplier") Supplier supplier ){
        supplierService.save(supplier);
        ModelAndView modelAndView = new ModelAndView("material/supplier/create");
        modelAndView.addObject("supplier", new Supplier());
        modelAndView.addObject("message", "New Supplier created successfully");
        return modelAndView;
    }
    @GetMapping("/edit-supplier/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Supplier supplier = supplierService.findbyId(id);
        if(supplier != null) {
            ModelAndView modelAndView = new ModelAndView("material/supplier/edit");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-supplier")
    public ModelAndView updateClassroom(@ModelAttribute("supplier") Supplier supplier){
        supplierService.save(supplier);
        ModelAndView modelAndView = new ModelAndView("material/supplier/edit");
        modelAndView.addObject("supplier", supplier);
        modelAndView.addObject("message", "Supplier updated successfully");
        return modelAndView;
    }
    @GetMapping("/delete-supplier/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Supplier supplier = supplierService.findbyId(id);
        if(supplier != null) {
            ModelAndView modelAndView = new ModelAndView("material/supplier/delete");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-supplier")
    public String deleteClassroom(@ModelAttribute("supplier") Supplier supplier){
        supplierService.remove(supplier.getId());
        return "redirect:suppliers";
    }

    @GetMapping("/view-supplier/{id}")
    public ModelAndView viewClassroom(@PathVariable("id") Long id){
        Supplier supplier = supplierService.findbyId(id);
        if(supplier == null){
            return new ModelAndView("error.404");
        }

        Iterable<Material> materials = materialService.findAllBySupplier(supplier);

        ModelAndView modelAndView = new ModelAndView("material/supplier/view");
        modelAndView.addObject("supplier", supplier);
        modelAndView.addObject("material", materials);
        return modelAndView;
    }
}
