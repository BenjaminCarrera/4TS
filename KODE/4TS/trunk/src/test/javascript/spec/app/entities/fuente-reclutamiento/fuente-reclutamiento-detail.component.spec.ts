/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { FuenteReclutamientoDetailComponent } from 'app/entities/fuente-reclutamiento/fuente-reclutamiento-detail.component';
import { FuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';

describe('Component Tests', () => {
  describe('FuenteReclutamiento Management Detail Component', () => {
    let comp: FuenteReclutamientoDetailComponent;
    let fixture: ComponentFixture<FuenteReclutamientoDetailComponent>;
    const route = ({ data: of({ fuenteReclutamiento: new FuenteReclutamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FuenteReclutamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuenteReclutamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuenteReclutamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fuenteReclutamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
