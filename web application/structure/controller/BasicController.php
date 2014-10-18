<?php

abstract class BasicController extends HyperMVCController{
    
    
    public function beforeAction() {
        parent::beforeAction();
        
    }
    
    public function afterRender() {
        parent::afterRender();
       
    }
    
}

