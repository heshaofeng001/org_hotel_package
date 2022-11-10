package org.hotel.packages.web.controller.customermanage;

import org.hotel.packages.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * spring 框架，递归查询离子类最近类（子类自己和父类）的RequestMapping，因此类上的RequestMapping无法拼接，同时子类不要打RequestMapping标
 *
 * @author he_sh
 * @version 2022-11月-10
 **/
@RequestMapping("/hotel/packages/customer")
public class AbstractCustomerController extends BaseController {
}
