import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appRed]'
})
export class RedDirective {

  constructor(private elementRef: ElementRef) {
    this.elementRef.nativeElement.style.color = "#e35e6b";
   }

  
}
