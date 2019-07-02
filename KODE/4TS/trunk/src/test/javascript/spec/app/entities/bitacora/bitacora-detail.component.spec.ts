/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BitacoraDetailComponent } from 'app/entities/bitacora/bitacora-detail.component';
import { Bitacora } from 'app/shared/model/bitacora.model';

describe('Component Tests', () => {
  describe('Bitacora Management Detail Component', () => {
    let comp: BitacoraDetailComponent;
    let fixture: ComponentFixture<BitacoraDetailComponent>;
    const route = ({ data: of({ bitacora: new Bitacora(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [BitacoraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BitacoraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BitacoraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bitacora).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
