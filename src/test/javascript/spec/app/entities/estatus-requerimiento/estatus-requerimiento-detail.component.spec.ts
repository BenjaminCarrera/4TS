/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusRequerimientoDetailComponent } from 'app/entities/estatus-requerimiento/estatus-requerimiento-detail.component';
import { EstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';

describe('Component Tests', () => {
  describe('EstatusRequerimiento Management Detail Component', () => {
    let comp: EstatusRequerimientoDetailComponent;
    let fixture: ComponentFixture<EstatusRequerimientoDetailComponent>;
    const route = ({ data: of({ estatusRequerimiento: new EstatusRequerimiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusRequerimientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusRequerimientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusRequerimientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusRequerimiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
