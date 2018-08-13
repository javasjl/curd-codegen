package com.justdebugit.codegen.outconfig;

import java.util.List;

import org.springframework.stereotype.Component;

import com.justdebugit.codegen.variables.ModelVar;
import com.justdebugit.codegen.variables.ModelsVar;

@Component
public class ModelOut implements OuterConfig{
  
  @CodeGen("model/model")
  public List<ModelVar> render(ModelsVar modelsVar){
    return modelsVar.models();
  }
  
}
