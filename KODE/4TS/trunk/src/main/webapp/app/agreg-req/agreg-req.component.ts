import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'jhi-agreg-req',
  templateUrl: './agreg-req.component.html',
  styleUrls: [
    'agreg-req.component.scss'
  ]
})
export class AgregReqComponent implements OnInit {

   // Enfoque del mapa
   lat = 19.4284700;
   lng = -99.1276600;
   zoom = 14;

  selected1 = new FormControl(0);
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  fruits: string[] = ['PHP'];
  allFruits: string[] = ['PHP', 'Java', 'Angular', 'Python'];
  visible2 = true;
  selectable2 = true;
  removable2 = true;
  addOnBlur2 = true;
  separatorKeysCodes2: number[] = [ENTER, COMMA];
  fruitCtrl2 = new FormControl();
  filteredFruits2: Observable<string[]>;
  fruits2: string[] = ['Java!'];
  allFruits2: string[] = ['PHP', 'Java', 'Angular', 'Python'];
  visible3 = true;
  selectable3 = true;
  removable3 = true;
  addOnBlur3 = true;
  separatorKeysCodes3: number[] = [ENTER, COMMA];
  fruitCtrl3 = new FormControl();
  filteredFruits3: Observable<string[]>;
  fruits3: string[] = ['Python!'];
  allFruits3: string[] = ['PHP', 'Java', 'Angular', 'Python'];
  @ViewChild('fruitInput3', {static: false}) fruitInput3: ElementRef<HTMLInputElement>;
  @ViewChild('auto3', {static: false}) matAutocomplete3: MatAutocomplete;
  @ViewChild('fruitInput2', {static: false}) fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('auto2', {static: false}) matAutocomplete2: MatAutocomplete;
  @ViewChild('fruitInput', { static: false }) fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;

  constructor() {
    this.filteredFruits = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => fruit ? this._filter(fruit) : this.allFruits.slice()));
      // Inicio Segundo chip autocompletable
      this.filteredFruits2 = this.fruitCtrl2.valueChanges.pipe(
          startWith(null),
          map((fruit2: string | null) => fruit2 ? this._filter2(fruit2) : this.allFruits2.slice()));
      // Fin Segundo chip autocompletable
      // Inicio Segundo chip autocompletable
      this.filteredFruits3 = this.fruitCtrl3.valueChanges.pipe(
          startWith(null),
          map((fruit3: string | null) => fruit3 ? this._filter3(fruit3) : this.allFruits3.slice()));
      // Fin Segundo chip autocompletable
  }
  siguiente() {
    this.selected1.setValue(1);
  }
  selected(event: MatAutocompleteSelectedEvent): void {
    this.fruits.push(event.option.viewValue);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
  }
  remove(fruit: string): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl.setValue(null);
    }
  }
  selected2(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits2.push(event.option.viewValue);
    this.fruitInput2.nativeElement.value = '';
    this.fruitCtrl2.setValue(null);
    private _filter2(value: string): string[] {
      const filterValue2 = value.toLowerCase();
      return this.allFruits2.filter(fruit2 => fruit2.toLowerCase().indexOf(filterValue2) === 0);
    }
  remove2(fruit2: string): void {
    // Inicio primer chip autocompletable
    const index2 = this.fruits2.indexOf(fruit2);
    if (index2 >= 0) {
      this.fruits2.splice(index2, 1);
    }
    // Fin primer chip autocompletable
  }
  add2(event: MatChipInputEvent): void {
    // Inicio primer chip autocompletable

    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete2.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits2.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl2.setValue(null);
    }
    // Fin primer chip autocompletable
  }
  selected3(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits3.push(event.option.viewValue);
    this.fruitInput3.nativeElement.value = '';
    this.fruitCtrl3.setValue(null);
    private _filter3(value: string): string[] {
      const filterValue3 = value.toLowerCase();
      return this.allFruits3.filter(fruit3 => fruit3.toLowerCase().indexOf(filterValue3) === 0);
    }
  remove3(fruit3: string): void {
    // Inicio primer chip autocompletable
    const index3 = this.fruits3.indexOf(fruit3);
    if (index3 >= 0) {
      this.fruits3.splice(index3, 1);
    }
    // Fin primer chip autocompletable
  }
  add3(event: MatChipInputEvent): void {
    // Inicio primer chip autocompletable

    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete3.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits3.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl3.setValue(null);
    }
    // Fin primer chip autocompletable
  }
  ngOnInit(): void {
  }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allFruits.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
  }
}
