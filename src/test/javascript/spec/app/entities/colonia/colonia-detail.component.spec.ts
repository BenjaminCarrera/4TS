/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ColoniaDetailComponent } from 'app/entities/colonia/colonia-detail.component';
import { Colonia } from 'app/shared/model/colonia.model';

describe('Component Tests', () => {
  describe('Colonia Management Detail Component', () => {
    let comp: ColoniaDetailComponent;
    let fixture: ComponentFixture<ColoniaDetailComponent>;
    const route = ({ data: of({ colonia: new Colonia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ColoniaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ColoniaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColoniaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.colonia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
