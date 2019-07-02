/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ReferenciasLaboralesDetailComponent } from 'app/entities/referencias-laborales/referencias-laborales-detail.component';
import { ReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';

describe('Component Tests', () => {
  describe('ReferenciasLaborales Management Detail Component', () => {
    let comp: ReferenciasLaboralesDetailComponent;
    let fixture: ComponentFixture<ReferenciasLaboralesDetailComponent>;
    const route = ({ data: of({ referenciasLaborales: new ReferenciasLaborales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReferenciasLaboralesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReferenciasLaboralesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferenciasLaboralesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.referenciasLaborales).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
