var USM = window.USM || {};

(function(window, document, $, USM) {
    "use strict";
    USM.validationUtil = (function() {

        var validationRules = function() {

            $('#LoginForm').bootstrapValidator({
                live: 'enabled',
                message: 'This value is not valid',
                fields: {
                    username: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Username'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Password'
                            }
                        }
                    }
                }
            });

            $('#NewUserForm').bootstrapValidator({
                live: 'enabled',
                excluded: [':disabled'],
                message: 'This value is not valid',
                fields: {
                    username: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Username'
                            },
                            stringLength: {
                            	min: 6,
                                max: 30,
                                message: 'The Username is Min 6 and Max 30 Characters'
                            },
                            remote: {
                            	message: 'Username Already Exists',
                            	url: USM.baseURL+'/secure/user/available.mk',
                                type: 'POST',
                                data : USM.data_csrf
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Password'
                            },
                            stringLength: {
                            	min: 8,
                            	max: 30,
                                message: 'The password should be min 8 and max 30 characters'
                            },
                            regexp:{
                                regexp: /^(?=.*\d)(?=.*[a-z]).{8,30}/,
                               message: 'The password must have at least 1 Lowercase and 1 Numeric Character'
                            }
                        }
                    },
                    role: {
                        validators: {
                            notEmpty: {
                                message: 'Please Select User Role'
                            }
                        }
                    }
                }
            });

            $('#ChangePwdForm').bootstrapValidator({
                live: 'enabled',
                excluded: [':disabled'],
                message: 'This value is not valid',
                fields: {
                    currentPassword: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Current Password'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter New Password'
                            },
                            stringLength: {
                            	min: 8,
                            	max: 30,
                                message: 'The password should be min 8 and max 30 characters'
                            },
                            regexp:{
                                regexp: /^(?=.*\d)(?=.*[a-z]).{8,30}/,
                               message: 'The password must have at least 1 Lowercase and 1 Numeric Character'
                            }
                        }
                    }
                }
            });

            $('#CategoryForm').bootstrapValidator({
                live: 'enabled',
                message: 'This value is not valid',
                fields: {
                    categoryName: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Category Name'
                            }
                        }
                    },
					categoryImage: {
                        validators: {
                            notEmpty: {
                                message: 'Please Upload Category Image'
                            },
                            file: {
                            	extension: 'jpg,jpeg,png,gif,bmp',
                                type: 'image/jpeg,image/png,image/gif,image/bmp',
                                maxSize: 2048 * 1024, // 2 MB
                                message: 'The selected file should be jpg/png/gif/bmp with max 2MB size'
                            }
                        }
                    }
                }
            });

         	$('#ProductForm').bootstrapValidator({
                live: 'enabled',
                message: 'This value is not valid',
                fields: {
                    productName: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Product Name'
                            }
                        }
                    },
					productImage: {
                        validators: {
                            notEmpty: {
                                message: 'Please Upload Product Image'
                            },
                            file: {
                            	extension: 'jpg,jpeg,png,gif,bmp',
                                type: 'image/jpeg,image/png,image/gif,image/bmp',
                                maxSize: 2048 * 1024, // 2 MB
                                message: 'The selected file should be jpg/png/gif/bmp with max 2MB size'
                            }
                        }
                    },
                    parentCategory: {
                    	validators: {
                            notEmpty: {
                                message: 'Please Select Product Category'
                            }
                        }
                    },
                    unit: {
                    	trigger: 'blur',
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Product Unit'
                            },
                            stringLength: {
                            	max: 8,
                                message: 'The Unit is too Large'
                            }
                        }
                    },
                    measure: {
                    	validators: {
                            notEmpty: {
                                message: 'Please Enter Unit Measure'
                            }
                        }
                    },
                    mrp: {
                    	trigger: 'blur',
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Product MRP Rate'
                            },
                            stringLength: {
                            	max: 8,
                                message: 'The MRP Rate is too Large'
                            }
                        }
                    },
                    saleRate: {
                    	trigger: 'blur',
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Product Sale Rate'
                            },
                            stringLength: {
                            	max: 8,
                                message: 'The Sale Rate is too Large'
                            }
                        }
                    },
                    transportation: {
                    	trigger: 'blur',
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Transportation charge'
                            },
                            stringLength: {
                            	max: 8,
                                message: 'The Transportation charge is too Large'
                            }
                        }
                    }
                }
            });

            $('#SliderForm').bootstrapValidator({
                live: 'enabled',
                message: 'This value is not valid',
                fields: {
					sliderImage: {
                        validators: {
                            notEmpty: {
                                message: 'Please Upload Slider Image'
                            },
                            file: {
                            	extension: 'jpg,jpeg,png,gif,bmp',
                                type: 'image/jpeg,image/png,image/gif,image/bmp',
                                maxSize: 2048 * 1024, // 2 MB
                                message: 'The selected file should be jpg/png/gif/bmp with max 2MB size'
                            }
                        }
                    },
                    description: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Slider Image Description'
                            }
                        }
                    },
                    sliderName: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Slider Name'
                            }
                        }
                    }
                }
            });

            $('#PromotionForm').bootstrapValidator({
                live: 'enabled',
                message: 'This value is not valid',
                fields: {
					promotionImage: {
                        validators: {
                            notEmpty: {
                                message: 'Please Upload Promotion Image'
                            },
                            file: {
                            	extension: 'jpg,jpeg,png,gif,bmp',
                                type: 'image/jpeg,image/png,image/gif,image/bmp',
                                maxSize: 2048 * 1024, // 2 MB
                                message: 'The selected file should be jpg/png/gif/bmp with max 2MB size'
                            }
                        }
                    },
                    description: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Promotion Description'
                            }
                        }
                    },
                    promotionName: {
                        validators: {
                            notEmpty: {
                                message: 'Please Enter Promotion Name'
                            }
                        }
                    }
                }
            });

            $('#ResetButton').on('click touchstart', function() {
                $('#NewUserForm, #ChangePwdForm, #CategoryForm, #ProductForm, #SliderForm, #PromotionForm').data('bootstrapValidator').resetForm();

                $('.checkbox, .radio').each(function(){
                    if($(this).hasClass('checked')){
                       $(this).removeClass('checked');
                    }
                });
            });
        };

        /* Return public properties/methods */
        return {
            initFunction: validationRules()
        };

    }());

}(window, document, jQuery, USM));

/* Bind the validation utilities function to document load */
jQuery(USM.validationUtil.initFunction);