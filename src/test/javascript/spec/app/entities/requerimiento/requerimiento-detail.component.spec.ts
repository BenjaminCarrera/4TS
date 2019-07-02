/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { RequerimientoDetailComponent } from 'app/entities/requerimiento/requerimiento-detail.component';
import { Requerimiento } from 'app/shared/model/requerimiento.model';

describe('Component Tests', () => {
  describe('Requerimiento Management Detail Component', () => {
    let comp: RequerimientoDetailComponent;
    let fixture: ComponentFixture<RequerimientoDetailComponent>;
    const route = ({ data: of({ requerimiento: new Requerimiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [RequerimientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RequerimientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequerimientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requerimiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
