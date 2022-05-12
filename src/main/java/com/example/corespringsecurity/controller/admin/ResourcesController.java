package com.example.corespringsecurity.controller.admin;


import com.example.corespringsecurity.domain.Resources;
import com.example.corespringsecurity.domain.Role;
import com.example.corespringsecurity.domain.RoleResources;
import com.example.corespringsecurity.dto.ResourcesDto;
import com.example.corespringsecurity.repository.RoleRepository;
import com.example.corespringsecurity.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.example.corespringsecurity.service.ResourcesService;
import com.example.corespringsecurity.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ResourcesController {
	
	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UrlFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	@GetMapping(value="/admin/resources")
	public String getResources(Model model) throws Exception {

		List<Resources> resources = resourcesService.getResources();
		model.addAttribute("resources", resources);

		return "admin/resource/list";
	}

	@PostMapping(value="/admin/resources")
	public String createResources(ResourcesDto resourcesDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
		Resources resources = modelMapper.map(resourcesDto, Resources.class);
		List<RoleResources> roleResources = new ArrayList<>();
		roleResources.add(new RoleResources(resources, role));
		resources.setRoleResources(roleResources);

		resourcesService.createResources(resources);
		filterInvocationSecurityMetadataSource.reload();

		return "redirect:/admin/resources";
	}

	@GetMapping(value="/admin/resources/register")
	public String viewRoles(Model model) throws Exception {

		List<Role> roleList = roleService.getRoles();
		model.addAttribute("roleList", roleList);

		ResourcesDto resources = new ResourcesDto();
//		Set<Role> roleSet = new HashSet<>();
//		roleSet.add(new Role());
//		resources.setRoleSet(roleSet);

//		List<RoleResources> roleResourcesSet = new ArrayList<>();
//		roleResourcesSet.add(new RoleResources());
//		resources.setRoleResources(roleResourcesSet);
		model.addAttribute("resources", resources);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/{id}")
	public String getResources(@PathVariable String id, Model model) throws Exception {

		List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
		Resources resources = resourcesService.getResources(Long.valueOf(id));

		ModelMapper modelMapper = new ModelMapper();
		ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
		List<String> roleNames = resourcesDto.getRoleResources().stream()
				.map(rr -> rr.getRole().getRoleName())
				.collect(Collectors.toList());
		resourcesDto.setRoleNames(roleNames);

		model.addAttribute("resources", resourcesDto);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/delete/{id}")
	public String removeResources(@PathVariable String id, Model model) throws Exception {

		Resources resources = resourcesService.getResources(Long.valueOf(id));
		resourcesService.deleteResources(Long.valueOf(id));
		filterInvocationSecurityMetadataSource.reload();

		return "redirect:/admin/resources";
	}
}
