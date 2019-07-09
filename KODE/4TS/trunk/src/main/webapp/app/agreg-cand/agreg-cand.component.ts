import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Component, ElementRef, ViewChild, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent, MatAutocomplete} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
// Inicio datatable
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

export interface Skills {
  Skills: string;
  Dominio: string;
  Calificacion: string;
  Eliminar: string;
}

@Component({
  selector: 'jhi-agreg-cand',
  templateUrl: './agreg-cand.component.html',
  styleUrls: [
    'agreg-cand.component.scss'
  ]
})
export class AgregCandComponent implements OnInit {

  // Variables Skills
  DATA_SKILLS: Skills[] = [
    { Skills: 'Hibernate', Dominio: 'Intermedio', Calificacion: '10.0', Eliminar: 'Eliminar' },
    { Skills: 'Angular', Dominio: 'Avanzado', Calificacion: '9.0', Eliminar: 'Eliminar' },
    { Skills: 'Java', Dominio: 'Principiante', Calificacion: '7.0', Eliminar: 'Eliminar' },
  ];
  dataSourceSkills: Skills[];
  displayedColumnsSkills: string[] = ['Skills', 'Dominio', 'Calificacion', 'Eliminar'];
// Inicio datatable
@ViewChild(MatPaginator, {static: true}) paginator2: MatPaginator;
// Fin datatable
  // Inicio primer chip autocompletable
  selecteds = new FormControl(0);
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  fruits: string[] = ['EUSA'];
  allFruits: string[] = ['AXA', 'AXOV', 'AXSI', 'BAZ'];
  @ViewChild('fruitInput', {static: false}) fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', {static: false}) matAutocomplete: MatAutocomplete;
  // Fin primer chip autocompletable
  // Inicio Segundo chip autocompletable
  visible2 = true;
  selectable2 = true;
  removable2 = true;
  addOnBlur2 = true;
  separatorKeysCodes2: number[] = [ENTER, COMMA];
  fruitCtrl2 = new FormControl();
  filteredFruits2: Observable<string[]>;
  fruits2: string[] = ['Listo!'];
  allFruits2: string[] = ['Listo!', 'AXOV', 'AXSI', 'BAZ'];
  @ViewChild('fruitInput2', {static: false}) fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('auto2', {static: false}) matAutocomplete2: MatAutocomplete;
  // Fin Segundo chip autocompletable

  message: string;

  constructor() {
    this.message = 'NuevCandComponent message';
    // Inicio primer chip autocompletable
    this.filteredFruits = this.fruitCtrl.valueChanges.pipe(
        startWith(null),
        map((fruit: string | null) => fruit ? this._filter(fruit) : this.allFruits.slice()));
    // Fin primer chip autocompletable
    // Inicio Segundo chip autocompletable
    this.filteredFruits2 = this.fruitCtrl2.valueChanges.pipe(
        startWith(null),
        map((fruit2: string | null) => fruit2 ? this._filter(fruit2) : this.allFruits2.slice()));
    // Fin Segundo chip autocompletable
    this.dataSourceSkills = this.DATA_SKILLS.slice();
  }

  sortDataSkills(sort: MatSort) {
    const data = this.DATA_SKILLS.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSourceSkills = data;
      return;
    }

    this.dataSourceSkills = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'Skills': return compare(a.Skills, b.Skills, isAsc);
        case 'Dominio': return compare(a.Dominio, b.Dominio, isAsc);
        case 'Calificacion': return compare(a.Calificacion, b.Calificacion, isAsc);
        case 'Eliminar': return compare(a.Eliminar, b.Eliminar, isAsc);
        default: return 0;
      }
    });
  }

  add(event: MatChipInputEvent): void {
    // Inicio primer chip autocompletable

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

  siguiente() {
    this.selecteds.setValue(1);
  }
  remove2(fruit2: string): void {
    // Inicio primer chip autocompletable
    const index2 = this.fruits2.indexOf(fruit2);
    if (index2 >= 0) {
      this.fruits2.splice(index2, 1);
    }
    // Fin primer chip autocompletable
  }

  remove(fruit: string): void {
    // Inicio primer chip autocompletable
    const index = this.fruits.indexOf(fruit);
    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
  selected2(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits2.push(event.option.viewValue);
    this.fruitInput2.nativeElement.value = '';
    this.fruitCtrl2.setValue(null);
    private _filter(value: string): string[] {
      const filterValue2 = value.toLowerCase();
      return this.allFruits2.filter(fruit2 => fruit2.toLowerCase().indexOf(filterValue2) === 0);
    }
  selected(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits.push(event.option.viewValue);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
    public _filter(value: string): string[] {
      const filterValue = value.toLowerCase();
      return this.allFruits.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
    }

    ngOnInit() {
      // Inicio datatable
      this.dataSource2.paginator = this.paginator2;
      this.dataSource2.sort = this.sort2;
      // Fin datatable
    }
    // Fin primer chip autocompletable
    // Inicio primer chip autocompletable
    // Fin primer chip autocompletable
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
